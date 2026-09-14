package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  action {
    val x = variable(0)

    x set2 10.value
    x capture2 x + 1

    sequence {
      x set2 10.value
      x capture2 x + 1
    }

    frame.count.rem(2) select {
      x set2 20.value
      x set2 30.value
    }
  }
}