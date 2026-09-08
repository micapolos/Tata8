package micapolos.zexy2.runtime

import micapolos.tata8.Color
import micapolos.tata8.Font

class Label(
  var string: Value<String>,
  var position: Value<Position<Double>>,
  val color: Value<Color>,
  val font: Value<Font>,
  val shadow: Value<Boolean>,
) : Drawable {
  override val drawing: Drawing
    get() = Drawing { canvas ->
      val position = position()
      canvas.draw(string(), position.x().toInt(), position.y().toInt(), color(), font(), shadow())
    }
}

fun main() {
  Label(
    "Hello, world!".value,
    position(30.0, 30.0).value,
    Color.YELLOW.value,
    Font.mica.value,
    false.value
  ).show()
}
