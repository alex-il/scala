package parser

import scala.xml._
import java.net._
import scala.io.Source
import java.util.ArrayList

object SimpleXmlParser {
  def main(args: Array[String]) {
    println("Working ....")
    run11
    println("___end___")
  }
  //  val movies = List("Пыльная работа", "Читающий мысли", "Притворщик", "Касл, 6 сезон")
  val movies = List("Место преступления Нью-Йорк, 9 сезон"
//      , "В поле зрения, 3 сезон е6"
      , "В поле зрения"
//      , "Притворщик, 4 сезон"
      , "Притворщик"
      , "Декстер"
//      , "Земский доктор - Возвращение "
      , "Земский доктор"
      , "Касл"
      , "Мент в законе"
      , "Черный список"
      , "Пыльная работа"
      , "Пасечник"
      )
      
  def run11() {
    //    val all = new XmlReader()
    val rss = scala.xml.XML.load(new URL("http://tfile.me/rss"))
//    val rss1 = scala.xml.XML.loadFile("d:\\Downloads\\tfile.xml")
//println(rss)
    println("..run11..")
    var title = ""
    (rss \\ "item").foreach { item =>
      title = (item \ "title").text
      if (compareByName(title)) {
        println(title)
        val url = item \ "enclosure" \ "@url"

        println(url.text)
        println("-----------")
      }
    }
  }

  def compareByName(name: String): Boolean = {
    movies.foreach {
      m =>
        if (name contains m) {
          print("+ ")
          return true
        }
    }
    //    print(" - ")
    false
  }

  def run1() {
    val musicElem = scala.xml.XML.loadFile("d:\\Downloads\\music.xml")
    (musicElem \ "artist").foreach { artist =>
      println((artist \ "@name").text + "\n")
      val albums = (artist \ "album").foreach { album =>
        println("  " + (album \ "@title").text + "\n")
        val songs = (album \ "song").foreach { song =>
          println("    " + (song \ "@title").text)
        }
        println
      }
    }
  }

  def run2() {
    val rssFeed = <rss version="2.0">
                    <channel>
                      <item>
                        <y:c date="AA"></y:c>
                        <y:c date="AB"></y:c>
                        <y:c date="AC"></y:c>
                      </item>
                    </channel>
                  </rss>

    val sep = "\n----\n"

    for {
      channel <- rssFeed \ "channel"
      item <- channel \ "item"
      y <- item \ "c"
      date <- y \ "@date" if (date text).equals("AB")
    } yield {
      val s = List(channel, item, y, date).mkString(sep)
      println(s)
    }
  }
}