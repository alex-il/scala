object Exercise_09_4_1_a {
  def forall[A](p: A => Boolean)(xs: List[A]): Boolean = {
    def filter[A](p: A => Boolean)(xs: List[A]): List[A] = xs match {
      case Nil => Nil
      case y :: ys =>
        if (p(y)) y :: ys.filter(p)
        else Nil
    }

    filter(p)(xs).length == xs.length
  }

  def exists[A](p: A => Boolean)(xs: List[A]): Boolean = {
    def filter[A](p: A => Boolean)(xs: List[A]): List[A] = xs match {
      case Nil => xs
      case y :: ys =>
        if (p(y)) List(y)
        else ys.filter(p)
    }

    filter(p)(xs).length > 0
  }

  def main(args: Array[String]) {
    val xs = List(1, 2, 3, 4, 5, 0)

    assert(exists[Int](x => x > 3)(xs))
    assert(!exists[Int](x => x < 0)(xs))

    assert(forall[Int](x => x >= 0)(xs))
    assert(!forall[Int](x => x > 3)(xs))
    val m = collection.immutable.HashMap(0 -> 100, 1->1, 2 -> 2)
    
    print(m)
  }
}