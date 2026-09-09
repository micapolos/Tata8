package micapolos.zexy3.runtime

interface Animation {
  fun start() {}
  fun step(seconds: Double) = seconds
}

val noAnimation: Animation = object : Animation {}

fun pauseAnimation(secondsEvaluator: DoubleEvaluator) =
  object : Animation {
    var remainingSeconds = 0.0

    override fun start() {
      remainingSeconds = secondsEvaluator.eval()
    }

    override fun step(seconds: Double): Double {
      remainingSeconds -= seconds
      if (remainingSeconds >= 0.0) {
        return 0.0
      } else {
        val leftoverSeconds = -remainingSeconds
        remainingSeconds = 0.0
        return leftoverSeconds
      }
    }
  }
