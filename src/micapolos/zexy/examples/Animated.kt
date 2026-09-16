package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animated {
    val counter = variable(0)

    sequence {
      this pause 1
      counter add 10
      this pause 1
      counter add 20
      this pause 1
      counter add 30
      this pause 1
    }

    counter
  }.show()
}