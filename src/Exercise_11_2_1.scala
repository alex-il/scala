object Exercise_11_2_1 {
  def main(args: Array[String]) {
    var x = 0

    repeatLoop {x = x + 1; println(x)} (x < 5)
    assert(x == 5)

    repeatLoop {x = x - 1; println(x)} (until(x == 0))
    assert(x == 0)

    def until(condition: => Boolean): Boolean = !condition

    def repeatLoop(command: => Unit)(condition: => Boolean) {
      if (condition) {
        command
        repeatLoop {command} (condition)
      }
    }
  }
}