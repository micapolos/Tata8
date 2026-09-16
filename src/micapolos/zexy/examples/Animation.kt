package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(100)
  val speed = key.z.isPressed.ifTrue(5).orElse(1)
  val rightOffset = key.right.isPressed.ifTrue(speed).orElse(0)
  val leftOffset = key.left.isPressed.ifTrue(-speed).orElse(0)
  val animation = x.set(x + rightOffset + leftOffset).everyStep
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(position(x, 10))
    .with(animation)
    .show()
}