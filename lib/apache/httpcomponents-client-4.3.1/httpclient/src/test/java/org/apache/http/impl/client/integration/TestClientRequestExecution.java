/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.impl.client.integration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Client protocol handling tests.
 */
public class TestClientRequestExecution extends IntegrationTestBase {

    @Before
    public void setUp() throws Exception {
        startServer();
    }

    private static class SimpleService implements HttpRequestHandler {

        public SimpleService() {
            super();
        }

        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {
            response.setStatusCode(HttpStatus.SC_OK);
            final StringEntity entity = new StringEntity("Whatever");
            response.setEntity(entity);
        }
    }

    private static class FaultyHttpRequestExecutor extends HttpRequestExecutor {

        private static final String MARKER = "marker";

        private final String failureMsg;

        public FaultyHttpRequestExecutor(final String failureMsg) {
            this.failureMsg = failureMsg;
        }

        @Override
        public HttpResponse execute(
                final HttpRequest request,
                final HttpClientConnection conn,
                final HttpContext context) throws IOException, HttpException {

            final HttpResponse response = super.execute(request, conn, context);
            final Object marker = context.getAttribute(MARKER);
            if (marker == null) {
                context.setAttribute(MARKER, Boolean.TRUE);
                throw new IOException(failureMsg);
            }
            return response;
        }

    }

    @Test
    public void testAutoGeneratedHeaders() throws Exception {
        final int port = this.localServer.getServiceAddress().getPort();
        this.localServer.register("*", new SimpleService());

        final HttpRequestInterceptor interceptor = new HttpRequestInterceptor() {

            public void process(
                    final HttpRequest request,
                    final HttpContext context) throws HttpException, IOException {
                request.addHeader("my-header", "stuff");
            }

        };

        final HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(
                    final IOException exception,
                    final int executionCount,
                    final HttpContext context) {
                return true;
            }

        };

        this.httpclient = HttpClients.custom()
            .addInterceptorFirst(interceptor)
            .setRequestExecutor(new FaultyHttpRequestExecutor("Oppsie"))
            .setRetryHandler(requestRetryHandler)
            .build();

        final HttpClientContext context = HttpClientContext.create();

        final String s = "http://localhost:" + port;
        final HttpGet httpget = new HttpGet(s);

        final HttpResponse response = this.httpclient.execute(getServerHttp(), httpget, context);
        EntityUtils.consume(response.getEntity());

        final HttpRequest reqWrapper = context.getRequest();

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        final Header[] myheaders = reqWrapper.getHeaders("my-header");
        Assert.assertNotNull(myheaders);
        Assert.assertEquals(1, myheaders.length);
    }

    @Test(expected=ClientProtocolException.class)
    public void testNonRepeatableEntity() throws Exception {
        final int port = this.localServer.getServiceAddress().getPort();
        this.localServer.register("*", new SimpleService());

        final HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(
                    final IOException exception,
                    final int executionCount,
                    final HttpContext context) {
                return true;
            }

        };

        this.httpclient = HttpClients.custom()
            .setRequestExecutor(new FaultyHttpRequestExecutor("a message showing that this failed"))
            .setRetryHandler(requestRetryHandler)
            .build();

        final HttpClientContext context = HttpClientContext.create();

        final String s = "http://localhost:" + port;
        final HttpPost httppost = new HttpPost(s);
        httppost.setEntity(new InputStreamEntity(
                new ByteArrayInputStream(
                        new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 } ),
                        -1));

        try {
            this.httpclient.execute(getServerHttp(), httppost, context);
        } catch (final ClientProtocolException ex) {
            Assert.assertTrue(ex.getCause() instanceof NonRepeatableRequestException);
            final NonRepeatableRequestException nonRepeat = (NonRepeatableRequestException)ex.getCause();
            Assert.assertTrue(nonRepeat.getCause() instanceof IOException);
            Assert.assertEquals("a message showing that this failed", nonRepeat.getCause().getMessage());
            throw ex;
        }
    }

    @Test
    public void testNonCompliantURI() throws Exception {
        this.localServer.register("*", new SimpleService());
        this.httpclient = HttpClients.createDefault();

        final HttpClientContext context = HttpClientContext.create();
        final BasicHttpRequest request = new BasicHttpRequest("GET", "blah.:.blah.:.");
        final HttpResponse response = this.httpclient.execute(getServerHttp(), request, context);
        EntityUtils.consume(response.getEntity());

        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        final HttpRequest reqWrapper = context.getRequest();

        Assert.assertEquals("blah.:.blah.:.", reqWrapper.getRequestLine().getUri());
    }

    @Test
    public void testRelativeRequestURIWithFragment() throws Exception {
        this.localServer.register("*", new SimpleService());
        this.httpclient = HttpClients.createDefault();
        final HttpHost target = getServerHttp();

        final HttpGet httpget = new HttpGet("/stuff#blahblah");
        final HttpClientContext context = HttpClientContext.create();

        final HttpResponse response = this.httpclient.execute(target, httpget, context);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        EntityUtils.consume(response.getEntity());

        final HttpRequest request = context.getRequest();
        Assert.assertEquals("/stuff", request.getRequestLine().getUri());
    }

    @Test
    public void testAbsoluteRequestURIWithFragment() throws Exception {
        this.localServer.register("*", new SimpleService());
        this.httpclient = HttpClients.createDefault();
        final HttpHost target = getServerHttp();

        final URI uri = new URIBuilder()
            .setHost(target.getHostName())
            .setPort(target.getPort())
            .setScheme(target.getSchemeName())
            .setPath("/stuff")
            .setFragment("blahblah")
            .build();

        final HttpGet httpget = new HttpGet(uri);
        final HttpClientContext context = HttpClientContext.create();

        final HttpResponse response = this.httpclient.execute(httpget, context);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        EntityUtils.consume(response.getEntity());

        final HttpRequest request = context.getRequest();
        Assert.assertEquals("/stuff", request.getRequestLine().getUri());

        final List<URI> redirectLocations = context.getRedirectLocations();
        final URI location = URIUtils.resolve(uri, target, redirectLocations);
        Assert.assertEquals(uri, location);
    }

}
