package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.zexy2.ast.start

fun main() {
  val image = Font.image

  val x = variable(10.0)
  val y = x + 50.0
  val zoom = Mouse.isPressed.ifTrue(2.0).orElse(1.0).loggedAs("zoom")
  val string = "Hello, this is my new engine called ZEXY!!!"
  val font = Font.system

  val animation = inParallel(
    x.keepAdding(60.0),
    sprite
      .with(image)
      .with(image.center.position.anchor)
      .with(Mouse.position)
      .with(scale(zoom, zoom))
      .with(angle(x * 0.5)),
    label
      .with(string)
      .with(Color.GREEN)
      .with(font)
      .with(position(Screen.center.position.x - font.width(string) * 0.5, 10.0)))

  animation.start()
}