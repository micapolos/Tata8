package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  action {
    val x = variable(0)

    x bind2 10.value
    x set2 x + 1

    sequence {
      x bind2 10.value
      x set2 x + 1
    }

    frame.count.rem(2) select {
      x bind2 20.value
      x bind2 30.value
    }
  }
}