package micapolos.zexy2.runtime

import micapolos.DepressedChicken
import micapolos.tata8.Image

class Sprite(
  val image: Value<Image>,
  val position: Position<Double>
) : Drawable {
  override val drawing: Drawing
    get() = Drawing { canvas ->
      canvas.draw(image(), position.x().toInt(), position.y().toInt())
    }
}

fun sprite(image: Image, position: Position<Double>) = sprite(image.value, position)

fun sprite(image: Value<Image>, position: Position<Double>) = Sprite(image, position)

fun main() {
  sprite(DepressedChicken.images[0], position(0.0, 0.0)).show()
}