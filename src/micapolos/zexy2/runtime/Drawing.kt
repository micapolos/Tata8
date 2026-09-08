package micapolos.zexy2.runtime

import micapolos.DepressedChicken
import micapolos.tata8.Canvas
import micapolos.tata8.Game

fun interface Drawing {
  fun drawOn(canvas: Canvas)

  companion object
}

val Drawing.Companion.empty get() = Drawing {}

val <T> T.drawing
  get() = Drawing {
    Game.log(this)
  }

fun <T> T.drawingAs(label: String) = Drawing {
  Game.log(label, this)
}

fun drawing(vararg drawings: Drawing) = Drawing { canvas ->
  drawings.forEach { it.drawOn(canvas) }
}

fun Drawing.show() {
  animated.show()
}

fun main() {
  drawing(
    sprite(DepressedChicken.images[0].value, position(64.0, 0.0)).drawing,
    sprite(DepressedChicken.images[1].value, position(64.0, 32.0)).drawing,
    sprite(DepressedChicken.images[2].value, position(64.0, 64.0)).drawing,
    128.0.drawing,
    "this is a string".drawing
  ).show()
}