package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(100)
    .animated {
      everyFrame {
        it capture it + 1
      }
    }
    .show()
}