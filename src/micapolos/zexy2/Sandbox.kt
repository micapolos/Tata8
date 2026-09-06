package micapolos.zexy2

import micapolos.Blocks
import micapolos.zexy2.ast.start

fun main() {
  val image = loadImage(Blocks::class, "depressedChicken.png")

  val x = variable(10.0)
  val y = (x + 50.0)
  val scale = Mouse.isPressed.ifTrue(2.0).orElse(1.0).logged

  val animation = inParallel(
    x.keepAdding(60.0),
    animateSprite()
      .with(image)
      .with(image.center.position.anchor)
      .with(position(Screen.size.x - x - 100.0, Mouse.position.y))
      .with(scale(scale, scale))
      .with(angle(x)))

  animation.start()
}