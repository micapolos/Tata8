package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(0)

    race {
      everyStep { counter add 1 }
      this pause 3
    }

    counter.showAnimated()
  }
}