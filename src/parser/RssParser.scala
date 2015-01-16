package parser

import com.sun.syndication.io._
import com.sun.syndication.feed.synd._
import java.net.URL
import scala.collection.JavaConversions._
import java.net._
import java.io._
import _root_.java.io.Reader
import org.xml.sax.InputSource
import java.nio.file.Files

//import scala.xml._

object RssParser {

  def main(args: Array[String]) {
    urlToFile("http://tfile.me/rss")
  }

  def main1(args: Array[String]) {
    println("start")

    //    parse("http://tfile.me/forum/download.php?id=638298")
    val urls = List("http://tfile.me/rss")
    val patternStr = "Первородные"
    val movies = List("Пыльная работа", "Читающий мысли", "Притворщик", "Касл, 6 сезон")
    //    val urls = List("http://gamecity.ws/rss.xml")
    val sfi = new SyndFeedInput()

    urls.foreach(url => {
      val all = new XmlReader(new URL(url))
      val feed = sfi.build(new XmlReader(new URL(url)))

      val entries = feed.getEntries()
      val len = entries.size()

      println(feed.getTitle() + ", items: " + len)

      val iter = entries.listIterator()

      for (e <- entries) {
        val item: SyndEntryImpl = e.asInstanceOf[SyndEntryImpl]
        val title = item.getTitle()
        if (title contains (patternStr)) {
          println(title)
          val l: SyndEnclosureImpl = item.getEnclosures().get(0).asInstanceOf[SyndEnclosureImpl]
          val torrent = l.getUrl()
          println(torrent)
          println("---------------------------------------")
        }
      }
    })

    println("end")
  }

  /**
   *
   */
  def urlToFile(sUrl: String) = {
    var fileName = "d:/111.torrent"
    val USER_AGENT = "Mozilla/5.0";
    val url = new URL(sUrl)
    val conn = url.openConnection
    conn.setRequestProperty("User-Agent", USER_AGENT);
    val path = new File(fileName).toPath()
    Files.deleteIfExists(path)
    Files.copy(conn.getInputStream, path)
  }

  def inputStreamToString(is: InputStream): String = {
    val rd: BufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))
    val builder = new StringBuilder()
    try {
      var line = rd.readLine
      while (line != null) {
        builder.append(line + "\n")
        line = rd.readLine
      }
    } finally {
      rd.close
    }
    builder.toString
  }
}