package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  sequence {
    val counter = variable(0)

    race {
      everyFrame { counter add2 1 }
      this pause 3
    }

    counter.show2()
  }
}