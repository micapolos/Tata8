package micapolos.zexy2

import micapolos.DepressedChicken
import micapolos.tata8.Color
import micapolos.tata8.Quote
import micapolos.zexy2.live.start

fun main() {
  val fontImage = micaFontImage

  val x = newVariable(-200.0)
  val y = x + 50.0
  val zoom = Mouse.isPressed.ifTrue(2.0).orElse(1.0).loggedAs("zoom")
  val string = "Hello, this is my new engine called ZEXY!!!"
  val font = Key.Z.isPressed.ifTrue(koraFont).orElse(micaFont)

  val chickenImages = DepressedChicken.images

  val animation = inParallel(
    x.keepAdding(60.0),
//    Key.RIGHT.isPressed.ifTrue(x.keepAdding(60.0)).orElse(doNothing),
//    Key.LEFT.isPressed.ifTrue(x.keepAdding(-60.0)).orElse(doNothing),
    Camera.alignment.set(centerAlignment),
    sprite
      .with(fontImage)
      .with(centerAlignment)
      .with(scale(zoom, zoom))
      .with(angle(x * 0.5)),
    sprite
      .with(Quote.image)
      .with(centerBottomAlignment)
      .with(position(x, -60.0))
      .with(scale(0.25, 0.25))
      .with(parallax(0.25)),
    sprite
      .with(Quote.image)
      .with(centerBottomAlignment)
      .with(position(x, -40.0))
      .with(scale(0.5, 0.5))
      .with(parallax(0.5)),
    sprite
      .with(Quote.image)
      .with(centerBottomAlignment)
      .with(position(x, 0.0)),
    sprite
      .with(chickenImages[x.times(0.125).int.floorMod(8)])
      .with(centerBottomAlignment)
      .with(position(0.0, 40.0)),
    label
      .with(string)
      .with(Color.GREEN)
      .with(centerTopAlignment)
      .with(font)
      .with(position(Screen.center.position.x, 10.0)))

  animation.start()
}