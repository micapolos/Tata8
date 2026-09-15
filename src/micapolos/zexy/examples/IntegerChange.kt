package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(0)
    val number = counter / 10
    val previous = variable(0)
    val current = variable(0)
    everyStep {
      counter add 1
      previous set current
      current set number
      current.isEqualTo(previous).not() logAs "changed"
    }
    current.showAnimated()
  }
}