package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(0)

    race {
      everyFrame { counter add 1 }
      this pause 3
    }

    counter.show2()
  }
}