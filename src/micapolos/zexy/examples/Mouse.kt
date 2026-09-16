package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    everyStep {
      mouse.position.x logAs "x"
      mouse.position.y logAs "y"
      mouse.isPressed logAs "is pressed"
    }
    noDrawing
  }
}

