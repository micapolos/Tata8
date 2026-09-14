package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(100)
    .animate {
      everyFrame {
        it add2 1
      }
    }
    .show()
}