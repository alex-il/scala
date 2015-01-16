package ttt

import java.io.FileWriter

object Test1 extends App {
  val fw = new FileWriter("d:/1.txt", true);
  fw.write("This line appended to file!\n");
  fw.write("2222222222!\n");
  fw.close()
  
  val pear = { "Oleg Br" }
  val Name(first, last) = pear
  println(first + " " + last)

  val t = "111 222222 333333333333".split(" ").sortWith(_.length < _.length)
  t.foreach(println(_))

  println(List(10, 5, 3, 1).reduceLeft(_ - _))
  println(List(10, 5, 3, 1).foldLeft(100)(_ - _))
  println(List(10, 5, 3, 1).reduceRight(_ - _))
  println(List(10, 5, 3, 1).foldRight(100)(_ - _))
  object Name {
    def unapply(input: String) = {
      Some("Oleg", "Ok")
    }
  }

  val freq = collection.mutable.Map[Char, Int]()
  for (c <- "Mississippi") freq(c) = freq.getOrElse(c, 0) + 1
  println(freq)

}