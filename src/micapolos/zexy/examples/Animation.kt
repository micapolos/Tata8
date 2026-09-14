package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(100)

  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(position(x, 10))
    .animate {
      everyFrame {
        val speed = key.z.isPressed.ifTrue(5).orElse(1)
        val rightOffset = key.right.isPressed.ifTrue(speed).orElse(0)
        val leftOffset = key.left.isPressed.ifTrue(-speed).orElse(0)

        x add2 rightOffset + leftOffset
      }
    }
    .show()
}