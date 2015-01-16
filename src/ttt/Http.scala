package ttt;

import java.io.OutputStreamWriter
import java.net.{ URLConnection, URL }
import collection.JavaConversions._
//import Implicits.wrapInputStream
import java.net.URLEncoder.encode

class Http(userAgent: String,
  encoding: String,
  HttpRequestTimeout: Int = 15000) {

  var cookies = Map[String, String]()

  private def loadCookies(conn: URLConnection) {
    for ((name, value) <- cookies) conn.setRequestProperty("Cookie", name + "=" + value)
  }

  private def saveCookies(conn: URLConnection) {
    conn.getHeaderFields.lift("Set-Cookie") match {
      case Some(cList) => cList foreach { c =>
        val (name, value) = c span { _ != '=' }
        cookies += name -> (value drop 1)
      }
      case None =>
    }
  }

  private def encodePostData(data: Map[String, String]) =
    (for ((name, value) <- data) yield encode(name, encoding) + "=" + encode(value, encoding)).mkString("&")

  def Get(url: String) = {
    val u = new URL(url)
    val conn = u.openConnection()
    conn.setRequestProperty("User-Agent", userAgent)
    conn.setConnectTimeout(HttpRequestTimeout)

    loadCookies(conn)

    conn.connect

    saveCookies(conn)

//    conn.getInputStream.mkString
  }

  def Post(url: String, data: Map[String, String]) = {
    val u = new URL(url)
    val conn = u.openConnection

    conn.setRequestProperty("User-Agent", userAgent)
    conn.setConnectTimeout(HttpRequestTimeout)

    loadCookies(conn)

    conn.setDoOutput(true)
    conn.connect

    val wr = new OutputStreamWriter(conn.getOutputStream())
    wr.write(encodePostData(data))
    wr.flush
    wr.close

    saveCookies(conn)

//    conn.getInputStream.mkString
  }
}