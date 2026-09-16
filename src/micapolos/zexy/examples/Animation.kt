package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    val x = variable(100)
    val speed = key.z.isPressed.ifTrue(5).orElse(1)
    val rightOffset = key.right.isPressed.ifTrue(speed).orElse(0)
    val leftOffset = key.left.isPressed.ifTrue(-speed).orElse(0)

    everyStep {
      x add rightOffset + leftOffset
    }

    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(x, 10))
  }
}