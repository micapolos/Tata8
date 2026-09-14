package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val counter = variable(0)

    parallel {
      everyFrame {
        counter add2 1
      }

      startOn(mouse.press) {
        counter set2 0
      }
    }

    counter.show2()
  }
}