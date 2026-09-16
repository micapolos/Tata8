package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showValue {
    val x = variable(0)
    repeat {
      sequence {
        this pause 1
        x add 1
      }
    }
    x
  }
}