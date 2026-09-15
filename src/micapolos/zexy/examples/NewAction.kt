package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val x = variable(0)

    x bind2 10.value
    x set2 x + 1

    sequence {
      x bind2 10.value
      x set2 x + 1
    }

    frame.count2.rem(2) selectStep {
      x bind2 20.value
      x bind2 30.value
    }
  }
}