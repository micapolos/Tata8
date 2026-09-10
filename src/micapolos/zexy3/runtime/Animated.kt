package micapolos.zexy3.runtime

import micapolos.tata8.Game

class Animated<out T>(val evaluator: Evaluator<T>, val animation: Animation)

fun Animated<Drawing>.show() {
  animation.start()
  evaluator.evalBoxed().drawOn(Game.background.canvas)
  Game.onUpdate = {
    animation.step(1/60.0)
    Game.background.canvas.clear()
    evaluator.evalBoxed().drawOn(Game.background.canvas)
  }
  Game.start()
}

fun <T> Animated<T>.logged(label: String?): Animated<T> =
  Animated(evaluator, animation)


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
