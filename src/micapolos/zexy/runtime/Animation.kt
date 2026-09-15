package micapolos.zexy.runtime

import kotlin.math.min

interface Animation {
  fun start() {}
  fun step(seconds: Double) = 0.0
}

val infiniteAnimation: Animation = object : Animation {}

val instantAnimation: Animation = object : Animation {
  override fun step(seconds: Double): Double = seconds
}

fun instantAnimation(action: Action) =
  object : Animation {
    override fun start() {
      action.execute()
    }

    override fun step(seconds: Double): Double = seconds
  }

fun pauseAnimation(secondsEvaluator: Evaluator<Double>) =
  pauseAnimation { secondsEvaluator.evalDouble() }

fun pauseAnimation(seconds: () -> Double) =
  object : Animation {
    var remainingSeconds = 0.0

    override fun start() {
      remainingSeconds = seconds()
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

fun parallel(animations: List<Evaluator<Animation>>): Animation =
  object : Animation {
    override fun start() {
      animations.forEach {
        it.evalObject().start()
      }
    }

    override fun step(seconds: Double): Double {
      var remainingSeconds = seconds
      animations.forEach { remainingSeconds = min(remainingSeconds, it.evalObject().step(seconds)) }
      return remainingSeconds
    }
  }

fun everyStepAnimation(action: Action): Animation =
  object : Animation {
    override fun start() {
    }

    override fun step(seconds: Double): Double {
      action.execute()
      return 0.0
    }
  }

fun race(animations: List<Animation>): Animation =
  object : Animation {
    var isFinished = true

    override fun start() {
      animations.forEach(Animation::start)
      isFinished = false
    }

    override fun step(seconds: Double): Double {
      if (!isFinished) {
        var remainingSeconds = 0.0
        animations.forEach { remainingSeconds = Math.max(remainingSeconds, it.step(seconds)) }
        isFinished = remainingSeconds != 0.0
        return remainingSeconds
      } else {
        return seconds
      }
    }
  }

class SequenceAnimation(
  val animations: List<Animation>,
) : Animation {
  var index = 0
  var needsInit = true

  override fun start() {
    index = 0
    needsInit = true
  }

  override fun step(seconds: Double): Double {
    var remainingSeconds = seconds
    while (true) {
      if (index == animations.size) {
        return remainingSeconds
      } else {
        val animation = animations[index]
        if (needsInit) {
          animation.start()
          needsInit = false
        }
        remainingSeconds = animation.step(remainingSeconds)
        if (remainingSeconds == 0.0) {
          return 0.0
        } else {
          index++
          needsInit = true
        }
      }
    }
  }
}

fun Animation.repeatWhile(conditionEvaluator: Evaluator<Int>): Animation =
  repeatWhile { conditionEvaluator.evalInt() != 0 }

fun Animation.repeatWhile(condition: () -> Boolean): Animation =
  object : Animation {
    var done = false

    override fun start() {
      done = false
      this@repeatWhile.start()
    }

    override fun step(seconds: Double): Double {
      var needsInit = false
      var remainingSeconds = seconds
      while (true) {
        if (done) {
          return remainingSeconds
        } else if (needsInit) {
          this@repeatWhile.start()
          needsInit = false
        }

        remainingSeconds = this@repeatWhile.step(remainingSeconds)
        if (remainingSeconds == 0.0) {
          return 0.0
        } else if (condition()) {
          needsInit = true
        } else {
          done = true
        }
      }
    }
  }

fun startOnAnimation(triggerEvaluator: IntEvaluator, animationEvaluator: Evaluator<Animation>) =
  object : Animation {
    var startedAnimationEvaluator: Evaluator<Animation>? = null

    override fun start() {
      startedAnimationEvaluator = null
    }

    override fun step(seconds: Double): Double {
      if (triggerEvaluator.evalInt() != 0) {
        val animationEvaluator = animationEvaluator
        animationEvaluator.evalObject().start()
        startedAnimationEvaluator = animationEvaluator
      }

      val animationEvaluator = startedAnimationEvaluator
      if (animationEvaluator == null) {
        return 0.0
      } else {
        return animationEvaluator.evalObject().step(seconds)
      }
    }
  }

fun selectStepAnimation(indexEvaluator: IntEvaluator, animations: List<Evaluator<Animation>>) =
  object : Animation {
    override fun start() {
      animations.forEach {
        it.evalObject().start()
      }
    }

    override fun step(seconds: Double): Double {
      return animations[indexEvaluator.eval()].evalObject().step(seconds)
    }
  }
