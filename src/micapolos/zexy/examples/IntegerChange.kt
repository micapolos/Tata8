package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(0)
    val number = counter / 10
    val previous = variable(0)
    val current = variable(0)
    everyFrame {
      counter.logAs("incrementing counter")
      counter add 1
      counter.logAs("incremented counter")
      number.logAs("new number")
      previous.logAs("setting previous")
      previous set current
      previous.logAs("set previous")
      current.logAs("setting current")
      current set number
      current.logAs("set current")
    }
    counter.showAnimated()
  }
}