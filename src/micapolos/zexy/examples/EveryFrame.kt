package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(100)
    everyFrame { counter add2 1 }
    counter.show2()
  }
}