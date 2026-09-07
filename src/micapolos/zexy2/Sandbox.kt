package micapolos.zexy2

import micapolos.DepressedChicken
import micapolos.tata8.Color
import micapolos.tata8.Quote
import micapolos.zexy2.live.show

fun main() {
  val fontImage = micaFontImage
  val chickenImages = DepressedChicken.images

  val xVariable = liveVariable(-100.0)
  val x = xVariable.loggedAs("x")
  val textY = liveVariable(10.0)
  val zoom = Mouse.isPressed.ifTrue(2.0).orElse(1.0).loggedAs("zoom")
  val string = "Hello, this is my new engine called ZEXY!!!"
  val font = Key.Z.isPressed.ifTrue(koraFont).orElse(micaFont).logged
  val speed = liveVariable(60.0)

  show(
    Camera.alignment.set(centerAlignment),
    xVariable.keepAdding(speed).onlyIf(Key.RIGHT.isPressed),
    xVariable.keepAdding(-speed).onlyIf(Key.LEFT.isPressed),
    speed.set(60.0.live - speed).on(Key.Z.press),
    xVariable.set(-100.0).then(pause(5.0)).repeat,
    textY.add(frameTime * 60.0),
    drawSprite
      .with(fontImage)
      .with(centerAlignment)
      .with(scale(zoom, zoom))
      .with(angle(x * 0.5)),
    repeat(3) { index ->
      val factor = 4.shr(index)
      val scale = 1.0 / factor
      val y = 80 / factor - 80.0
      drawSprite
        .with(Quote.image)
        .with(centerBottomAlignment)
        .with(position(x, y))
        .with(scale(scale, scale))
        .with(parallax(scale))
    },
    drawSprite
      .with(chickenImages[x.times(0.125).int.floorMod(8)].loggedAs("chicken"))
      .with(centerBottomAlignment)
      .with(position(x, 40.0))
      .with(parallax(1.5)),
    drawLabel
      .with(string)
      .with(Color.GREEN)
      .with(centerTopAlignment)
      .with(font)
      .with(position(Screen.center.position.x, textY)))
}