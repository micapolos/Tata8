package micapolos.zexy.runtime

class Animated<out T>(val evaluator: Evaluator<T>, val animation: Evaluator<Animation>)

fun main() {
  Animated(
    ObjectEvaluator {
      Drawing { canvas ->
        canvas.drawRect(10, 10, 20, 20)
      }
    },
    ObjectEvaluator { infiniteAnimation }
  ).show()
}
