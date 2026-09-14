package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(0)
  rect
    .with(position(x, 10))
    .with(size(30, 30))
    .animated {
      x set 100.value
      pause(2)
    }
    .show()
}