package micapolos.ast

import micapolos.Blocks

fun main() {
  val image = image(Blocks::class, "depressedChicken.png")

  val xVariable = variable(10.0)
  val x = xVariable.readOnly.loggedAs("x")
  val y = (x + 50.0).loggedAs("y")

  val animation = animation(
    xVariable.keepAdding(60.0),
    sprite()
      .with(image)
      .withPosition(x * 2.0, y - 50.0)
      .withScale(1.0, 1.0)
      .withAngle(x))

  animation.show()
}