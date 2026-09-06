package micapolos.ast

import micapolos.Blocks

fun main() {
  val image = image(Blocks::class, "depressedChicken.png")

  val x = variable(10.0)
  val y = (x + 50.0)
  val b = constant(false).ifTrue(constant(123)).orElse(constant(123))
  val scale = isMouseButtonPressed.ifTrue(2.0).orElse(1.0).logged

  val animation = animation(
    x.keepAdding(60.0),
    sprite()
      .with(image)
      .withPosition(x * 2.0, y - 50.0)
      .withScale(scale, scale)
      .withAngle(x))

  animation.show()
}