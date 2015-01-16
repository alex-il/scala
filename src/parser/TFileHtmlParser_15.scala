package parser

import scala.xml.XML

object TFileHtmlParser15 {

	val loadedMovies = readLoadedMovies

	def main(args: Array[String]) {
    println("Working ....")
    runHtml_tFile
    println("___end___")
  }

  def readLoadedMovies:Set[String]={
    scala.io.Source.fromFile("d:/loadedMovies.txt")( "UTF-8").getLines().toSet
  }
  
  val encRus = "windows-1251"
  //  русские сериалы
  //  зарубежные сериалы
  val links = List("http://tfile.me/forum/viewforum.php?f=1591", "http://tfile.me/forum/viewforum.php?f=37")
  val tFileHost = "http://tfile.me"
  /**
   *
   */
  def runHtml_tFile() {

    for (l <- links) {
      val s = scala.io.Source.fromURL(l, encRus)
      val data = s.getLines.toList.filter(_ contains "New!").mkString("\n")
      //      println(data)
      tFileParser(XML.loadString("<rr>\n%s\n</rr>".format(data)))
      println("...................................................................")
    }
  }

  /**
   *
   */
  def tFileParser(xmlData: scala.xml.Elem) {
    var i = 1
    var title = ""
    (xmlData \\ "a").foreach { item =>
      title = item.text
      if (compareByName(title)) {
        println(i + ". " + title)
        i += 1
        val uri = (item \ "@href")
        val titleUrl = tFileHost + uri.text
        println(titleUrl)
        println(getDownloadLink(titleUrl))
        //        println("-----------")
      }
    }
  }

  /**
   *
   */
  def getDownloadLink(l: String): String = {
    val s = scala.io.Source.fromURL(l, encRus)
    val link = s.getLines.toList.filter(_ contains "href=\"download.php?id=").mkString
    subString(link)
  }

  def subString(s: String): String = {
    val старт = s.indexOf("download.php?id=")
    val end = s.indexOf("&")
    if (старт < end) {
      tFileHost + "/forum/" + s.substring(старт, end)
    }else{
      "=== error  ==="
    }
  }

  /**
   *
   */
  def compareByName(name: String): Boolean = {
    movies.foreach {
      m =>
        if (name contains m.trim) {
          if( loadedMovies contains name) return false
          print("+ ")
          return true
        }
    }
    false
  }

  val movies = FileReader.getMovies("d:/movies2download.txt")

  val movies2 = List(
    "Место преступления Нью-Йорк, 9 сезон", "В поле зрения", "Притворщик", "Декстер", "Земский доктор", "Касл", "Мент в законе", "Черный список", "Пыльная работа", "Пасечник", "Шеф", "Дело врачей", "Полиция Гавайев", "Стрела", "Гримм", "Killing", "Читающий", "Listener", "Criminal", "Pretender", "Розыскник", "Da Vinci's", "Части тела", "Мотив")

}
