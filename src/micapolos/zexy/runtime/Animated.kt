package micapolos.zexy.runtime

class Animated<out T>(val evaluator: Evaluator<T>, val animation: Animation)

fun Animated<Drawing>.show() {
  Game(animatedDrawing = this).show()
}

fun <T> animatedPulse(animatedHigh: Animated<T>, animatedLow: Animated<T>): Animated<T> = run {
  val pulseAnimation = PulseAnimation(animatedHigh.animation, animatedLow.animation)
  Animated(
    selectEvaluator({ if (pulseAnimation.isLow) 1 else 0 }, animatedHigh.evaluator, animatedLow.evaluator),
    pulseAnimation
  )
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
