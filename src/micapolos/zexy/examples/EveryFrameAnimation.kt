package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(100)
    .animated {
      everyFrame {
        it capture2 it + 1
      }
    }
    .show()
}