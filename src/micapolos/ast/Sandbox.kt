package micapolos.ast

import micapolos.Blocks

fun main() {
  val image = image(Blocks::class, "depressedChicken.png")

  val x = variable(10.0)
  val y = x + 50.0

  val animation = animation(
    x.keepAdding(60.0),
    sprite()
      .with(image)
      .withPosition(x * 2.0, y - 50.0)
      .withScale(1.0, 1.0)
      .withAngle(x))

  animation.show()
}