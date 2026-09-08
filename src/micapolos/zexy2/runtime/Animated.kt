package micapolos.zexy2.runtime

import micapolos.tata8.Color
import micapolos.tata8.Game
import micapolos.tata8.Shader

class Animated<T>(
  val value: Value<T>,
  val animation: Animation,
)

fun Animated<Drawing>.show() {
  var gameTime = 0f
  var isFinished = false

  Game.screen.shader = Shader.CRT_PHOSPHOR

  animation.start()

  Game.onStep = { seconds ->
    Game.background.canvas.clear()

    if (Game.keys.reset.pressed()) {
      gameTime = 0f
      isFinished = false
      IO.println("Start")
    }

    if (!isFinished) {
      value().drawOn(Game.background.canvas)

      val leftover = animation.step(seconds)
      if (leftover != 0f) {
        gameTime += seconds - leftover
        isFinished = true
      } else {
        gameTime += seconds
      }
    } else {
      val text = "Animation finished in $gameTime seconds. Press R to restart."
      val width = Game.font.width(text)
      Game.background.canvas.draw(text, (Game.WIDTH - width) / 2, 10, Color.YELLOW)
    }
  }

  Game.start()
}

val <T> T.animated get() = Animated(value, Animation.empty)

val Animation.animated get() = Animated(Drawing.empty.value, this)

