package micapolos.zexy2

import micapolos.Blocks
import micapolos.zexy2.ast.show

fun main() {
  val image = loadImage(Blocks::class, "depressedChicken.png")

  val x = variable(10.0)
  val y = (x + 50.0)
  val b = constant(false).ifTrue(constant(123)).orElse(constant(123))
  val scale = Mouse.isPressed.ifTrue(2.0).orElse(1.0).logged

  val animation = animation(
    x.keepAdding(60.0),
    sprite()
      .with(image)
      .withPosition(Mouse.position.x, Mouse.position.y)
      .withScale(scale, scale)
      .withAngle(x))

  animation.show()
}