package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    everyFrame {
      mouse.position.x logAs "x"
      mouse.position.y logAs "y"
      mouse.isPressed logAs "is pressed"
    }
  }.show()
}

