package micapolos.zexy3.runtime

import micapolos.tata8.Game
import micapolos.tata8.Shader
import micapolos.zexy3.label
import micapolos.tata8.Color as TataColor

class Animated<out T>(val evaluator: Evaluator<T>, val animation: Animation)

fun Animated<Drawing>.show() {
  animation.start()
  evaluator.evalBoxed().drawOn(Game.background.canvas)
  var gameSeconds = 0.0
  val stepSeconds = Game.FRAME_SECONDS.toDouble()
  Game.onUpdate = {
    Game.background.canvas.clear()
    val leftOverSeconds = animation.step(stepSeconds)
    val isFinished = leftOverSeconds != 0.0
    evaluator.evalBoxed().drawOn(Game.background.canvas)
    gameSeconds += stepSeconds
    if (isFinished) {
      gameSeconds -= leftOverSeconds
      val string = "Game finished in $gameSeconds seconds"
      val width = Game.font.width(string)
      Game.background.canvas.draw(string, (Game.WIDTH - width) / 2, 8, TataColor.YELLOW, Game.font, true)
    }
  }
  Game.screen.shader = Shader.CRT_PHOSPHOR
  Game.start()
}

fun <T> Animated<T>.logged(label: String?): Animated<T> =
  Animated(evaluator, animation)

fun <T> animatedRunWhileNotZero(evaluator: Evaluator<T>, condition: IntEvaluator, animation: Animation) =
  Animated(evaluator, animation.runWhileNotZero(condition))

fun main() {
  Animated(
    ObjectEvaluator {
      Drawing { canvas ->
        canvas.drawRect(10, 10, 20, 20)
      }
    },
    noAnimation
  ).show()
}
