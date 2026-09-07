package micapolos.zexy2.examples

import micapolos.DepressedChicken
import micapolos.tata8.Image
import micapolos.zexy2.*
import micapolos.zexy2.live.show

data class Item(val image: Image, val x: Int, val parallaxRatio: Double) {
  val draw = drawSprite
}
data class Scene(val items: List<Item>)

fun drawItem(image: Image, x: Int, parallaxRatio: Double) =
  drawSprite.with(image).with(centerTopAlignment).with(position(x.toDouble(), 0.0)).with(parallax(parallaxRatio))

val drawScene1 = parallel(
  drawItem(DepressedChicken.image, 100, 1.0),
)

fun main() {
  show(drawScene1)
}