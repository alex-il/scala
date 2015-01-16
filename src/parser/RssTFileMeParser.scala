package parser

import com.sun.syndication.io._
import com.sun.syndication.feed.synd._
import java.net.URL
import scala.collection.JavaConversions._
import java.net._
import java.io._
import _root_.java.io.Reader
import org.xml.sax.InputSource

//import scala.xml._

object RssTFileMeParser {

  def main(args: Array[String]) {
    println("Working ....")
    run11
    println("___end___")
  }

  val movies = List("Место преступления Нью-Йорк, 9 сезон" //      , "В поле зрения, 3 сезон е6"
  , "В поле зрения" //      , "Притворщик, 4 сезон"
  , "Притворщик", "Декстер" //      , "Земский доктор - Возвращение "
  , "Земский доктор"
  , "Касл"
  , "Мент в законе", "Черный список", "Пыльная работа"
  , "Пасечник", "Шеф", "Дело врачей", "Полиция Гавайев"
  , "Стрела", "Гримм", "Killing", "Читающий", "Listener"
  , "Щит", "Criminal", "Pretender", "Розыскник"
  , "Da Vinci's"
  , "Части тела"
  )

  def run11() {
    //    val all = new XmlReader()
    val rss = scala.xml.XML.load(new URL("http://tfile.me/rss"))
    //      val rss = scala.xml.XML.load(new URL("http://tfile.me/forum/viewforum.php?f=1591"))
    //    val rss1 = scala.xml.XML.loadFile("d:\\Downloads\\tfile.xml")
    var i = 1
    var title = ""
    (rss \\ "item").foreach { item =>
      title = (item \ "title").text
      if (compareByName(title)) {
        println(i +". "+title)
        i+=1
        val url = item \ "enclosure" \ "@url"

        println(url.text)
        println("-----------")
      }
    }
  }

  def runHTML() {
    val rss = scala.xml.XML.load(new URL("http://tfile.me/forum/viewforum.php?f=1591"))
    println(rss)
//    println("..runHTML.." + rss)
    (rss \\ "t").foreach { item =>
      val tClass = (item \ "@class").text
      if(tClass.equals("t")){
        val a = (item \ "a").text
        println("====::---------"+ a)
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

}