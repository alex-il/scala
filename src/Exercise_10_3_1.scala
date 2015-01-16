object Exercise_10_3_1 {
  def flattenOrg[A](xss: List[List[A]]): List[A] =
    (xss :\ (Nil: List[A]))((xs, ys) => xs ::: ys)

  def flatten[A](xss: List[List[A]]): List[A] =
    for (xs <- xss; x <- xs) yield x

  def main(args: Array[String]) {
    val xss = List(List(1, 2), List(3, 4))

    println (xss)
    println (flatten(xss) )
    println (flattenOrg(xss) )
    println ()
  }
}
