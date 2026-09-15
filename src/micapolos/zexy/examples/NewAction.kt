package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val x = variable(0)

    x bind 10.value
    x set x + 1

    sequence {
      x bind 10.value
      x set x + 1
    }

    frame.count.rem(2) selectStep {
      x bind 20.value
      x bind 30.value
    }
  }
}