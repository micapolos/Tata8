package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  action {
    val x = variable(0)

    x set 10.value
    x capture x + 1

    sequence {
      x set 10.value
      x capture x + 1
    }

    frame.count.rem(2) select {
      x set 20.value
      x set 30.value
    }
  }
}