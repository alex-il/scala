package ttt

import scala.sys.process._
import java.io.File
import java.net.{ URLConnection, URL }
import java.net._

object StreamTest {
  def main(args: Array[String]) {
    val enc = "UTF-8"
    //    println(scala.io.Source.fromURL("http://tfile.me/forum/", enc).mkString)
    //    println(scala.io.Source.fromURL("http://tfile.me/forum/download.php?id=638255", enc).mkString)
    //    val data = scala.io.Source.fromURL("http://www.informit.com/store/scala-for-the-impatient-9780321774095", enc).mkString.contains("description")
    Get("http://tfile.me/forum/download.php?id=638255")
//    val data = scala.io.Source.fromURL("http://tfile.me/forum/download.php?id=638255", "windows-1251").mkString
    //    System.err.println(data);

  }

  def Get(url: String) = {
    val u = new URL(url)
    val conn = u.openConnection()
    conn.setRequestProperty("User-Agent", "Post")

    conn.connect

    val is = conn.getInputStream
    println(is.read())
  }

  def runCommand() = {
    //    val s = "d:\\1.bat /d" #>> new File("1.out")!!;
    val s = "d:\\p\\7-Zip\\7zFM.exe" !
    //			 ProcessLogger.
    //    print(s)

  }

}