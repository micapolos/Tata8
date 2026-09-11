package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  mouse.isPressed
    .ifTrue("pressed")
    .orElse("released")
    .show()
}