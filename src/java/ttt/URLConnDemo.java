package java.ttt;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;

public class URLConnDemo {
//	org.apache.commons.io.FileUtils.copyURLToFile(URL, File)
	public static void main(String[] args) {
		try {
			
//			URL url = new URL("http://tfile.me/forum/download.php?id=638298");
			URL url = new URL("http://tfile.me");
			FileUtils.copyURLToFile(url, new File("d:/1.tst"));
			System.err.println("------------------");
			URLConnection urlConn = url.openConnection();
			HttpURLConnection httpUrlConn = (HttpURLConnection)urlConn;
			
			httpUrlConn.setRequestMethod("GET");
			System.out.println("type: "+ httpUrlConn.getContentType());
			System.out.println("content: "+ httpUrlConn.getContent());
			httpUrlConn.connect();
			InputStream inputStream = httpUrlConn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String urlString = "";
			String current;
			while ((current = in.readLine()) != null) {
				urlString += current;
			}
			System.out.println(urlString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void test(){
     
	}
}