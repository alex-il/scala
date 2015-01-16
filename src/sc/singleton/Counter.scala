package sc.singleton

class Counter {
  protected var c = 0
  def inc: Int = {
    c += 1
    println(c)
    c
  }
  
  def apply(name: String) {

    c += 1
    println("count:" + c + "; ");
  }

  def current = c
}