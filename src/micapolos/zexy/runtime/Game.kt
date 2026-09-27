package micapolos.zexy.runtime

class Game(
  val title: String = "Zexy game",
  val drawingEvaluator: Evaluator<Drawing> = ObjectEvaluator { Drawing { } },
  val animationEvaluator: Evaluator<Animation> = ObjectEvaluator { instantAnimation },
)

fun main() {
  Game(animationEvaluator = pauseAnimation(DoubleEvaluator { 1.0 }).let { ObjectEvaluator { it } }).show()
}