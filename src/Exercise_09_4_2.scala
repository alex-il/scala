object Exercise_09_4_2 {
  def flattenLeft[A](xs: List[List[A]]): List[A] =
    ((Nil: List[A]) /: xs){
      (xs, x) => xs ::: x
    }

  def flattenRight[A](xs: List[List[A]]): List[A] =
    (xs :\ (Nil: List[A])) {
      (x, xs) => x ::: xs
    }

  def main(args: Array[String]) {
    val xs = List(List(1, 2, 3), List(7, 8, 9))

    println(flattenRight(xs))
    println(flattenLeft(xs))
  }
}