package micapolos.zexy2

import micapolos.tata8.Font
import micapolos.zexy2.ast.start

fun main() {
  val image = Font.image

  val x = variable(10.0)
  val y = x + 50.0
  val zoom = Mouse.isPressed.ifTrue(2.0).orElse(1.0).loggedAs("zoom")

  val animation = inParallel(
    x.keepAdding(60.0),
    animateSprite
      .with(image)
      .with(image.center.position.anchor)
      .with(position(Screen.size.center.position.x, Mouse.position.y))
      .with(scale(zoom, zoom))
      .with(angle(x * 0.5)))

  animation.start()
}