package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(100)
    everyStep { counter add 1 }
    counter.showAnimated()
  }
}