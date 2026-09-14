package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(100)
    .animated { counter ->
      everyFrame {
        counter capture counter + 1
      }
    }
    .show()
}