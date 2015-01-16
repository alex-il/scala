package parser

import scala.xml.XML

object FileReader {
  val encRus = "windows-1251"
  val encUTF8 = "UTF-8"
  val ENC = encUTF8

  def main(args: Array[String]) {
    println("File Reader Working ....")
    val movies = getMovies("d:/movies2download.txt")
    for (m <- movies) {
      println(m)
    }
    println("___end___")
  }

  def getMovies(path: String): List[String] = {
    scala.io.Source.fromFile(path)(scala.io.Codec(ENC))
      .getLines.toList
      .filter(_.size > 0)
      .filter(_.head.!=('#'))
  }
}
