package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(0)
    .animate { counter ->
      this pause 1
      counter add2 10
      this pause 1
      counter add2 20
      this pause 1
      counter add2 30
      this pause 1
    }
    .show()
}