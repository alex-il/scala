
object t1 {

  def main(args: Array[String]) {
  }

  def main2(args: Array[String]) {
    try {
      val elems = args map Integer.parseInt
      println("Ars SUM: " + elems.foldRight(0)(_ + _))
    } catch {
      case e: NumberFormatException =>
        println("errrr. Usage: scala Main <#!> <#2> ... ")
    }
  }

  def main1(args: Array[String]) {

    val set1 = Set(("o1", 1, 6, "koo"), ("o1", 2), ("q1", 1, 1, "q1q1"))

    println(set1.head)
    println(set1.tail)
    println(set1.last)
    for (s <- set1) {
      println(s)
    }
    val t = ("o1", 1, 6, "koo")

    val set2 = (("o1", 1, 6, "koo"), ("o1", 2), ("q1", 1, 1, "q1q1"), ("q1", 1, 1, "q1q1", 1))

    print(1)
  }
}

 
