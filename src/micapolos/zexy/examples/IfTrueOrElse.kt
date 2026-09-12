package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  mouse.isPressed
    .ifTrue("mouse is pressed")
    .orElse("mouse is released")
    .show()
}