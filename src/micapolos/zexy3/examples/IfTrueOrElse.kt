package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  mouse.isPressed
    .ifTrue("mouse is pressed")
    .orElse("mouse is released")
    .show()
}