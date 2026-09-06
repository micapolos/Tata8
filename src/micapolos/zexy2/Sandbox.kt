package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.zexy2.live.start

fun main() {
  val image = micaFontImage

  val x = newVariable(10.0)
  val y = x + 50.0
  val zoom = Mouse.isPressed.ifTrue(2.0).orElse(1.0).loggedAs("zoom")
  val string = "Hello, this is my new engine called ZEXY!!!"
  val font = Key.Z.isPressed.ifTrue(koraFont).orElse(micaFont)

  val animation = inParallel(
    x.keepAdding(60.0),
    sprite
      .with(image)
      .with(centerAlignment)
      .with(Mouse.position)
      .with(scale(zoom, zoom))
      .with(angle(x * 0.5)),
    label
      .with(string)
      .with(Color.GREEN)
      .with(centerTopAlignment)
      .with(font)
      .with(position(Screen.center.position.x, 10.0)))

  animation.start()
}