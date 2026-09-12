package micapolos.zexy.runtime

import micapolos.tata8.Game
import micapolos.tata8.Shader
import micapolos.tata8.Color as TataColor

class Animated<out T>(val evaluator: Evaluator<T>, val animation: Animation)

fun Animated<Drawing>.show() {
  animation.start()
  evaluator.evalBoxed().drawOn(Game.background.canvas)
  val stepSeconds = Game.FRAME_SECONDS.toDouble()
  var gameSeconds = 0.0
  Game.onUpdate = {
    if (Game.keys.reset.pressed()) {
      animation.start()
      gameSeconds = 0.0
    }
    Game.background.canvas.clear()
    val leftOverSeconds = animation.step(stepSeconds)
    val isFinished = leftOverSeconds != 0.0
    evaluator.evalBoxed().drawOn(Game.background.canvas)
    gameSeconds += stepSeconds
    if (isFinished) {
      gameSeconds -= leftOverSeconds
      val string = "Finished in $gameSeconds seconds."
      val width = Game.font.width(string)
      Game.background.canvas.draw(string, (Game.WIDTH - width) / 2, 8, TataColor.YELLOW, Game.font, true)
    }
  }
  Game.screen.shader = Shader.CRT_PHOSPHOR
  Game.start()
}

fun main() {
  Animated(
    ObjectEvaluator {
      Drawing { canvas ->
        canvas.drawRect(10, 10, 20, 20)
      }
    },
    infiniteAnimation
  ).show()
}
