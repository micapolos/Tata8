package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(0)
    .withSequence { counter ->
      this pause 1
      counter add 10
      this pause 1
      counter add 20
      this pause 1
      counter add 30
      this pause 1
    }
    .show()
}