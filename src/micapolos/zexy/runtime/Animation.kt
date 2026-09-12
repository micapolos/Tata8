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

fun actionAnimation(execute: () -> Unit) =
  object : Animation {
    override fun start() {
      execute()
    }

    override fun step(seconds: Double): Double = seconds
  }

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

fun Animation.stretch(ratioEvaluator: DoubleEvaluator) =
  object : Animation {
    override fun start() {
      this@stretch.start()
    }

    override fun step(seconds: Double): Double {
      return this@stretch.step(seconds * ratioEvaluator.eval())
    }
  }

infix fun Animation.then(rhs: Animation) =
  object : Animation {
    override fun start() {
      this@then.start()
      rhs.start()
    }

    override fun step(seconds: Double): Double {
      val lhsSeconds = this@then.step(seconds)
      val rhsSeconds = rhs.step(seconds)
      return min(lhsSeconds, rhsSeconds)
    }

  }

fun parallel(vararg animations: Animation): Animation =
  parallel(animations.toList())

fun parallel(animations: List<Animation>): Animation =
  object : Animation {
    override fun start() {
      animations.forEach(Animation::start)
    }

    override fun step(seconds: Double): Double {
      var remainingSeconds = seconds
      animations.forEach { remainingSeconds = min(remainingSeconds, it.step(seconds)) }
      return remainingSeconds
    }
  }

fun everyFrameAnimation(animation: Animation): Animation =
  object : Animation {
    override fun start() {}

    override fun step(seconds: Double): Double {
      animation.start()
      animation.step(seconds)
      return 0.0
    }
  }

fun nextFrameAnimation(animation: Animation): Animation =
  object : Animation {
    var shouldStart = false
    var isRunning = false

    override fun start() {
      shouldStart = false
      isRunning = false
    }

    override fun step(seconds: Double): Double {
      if (!shouldStart) {
        shouldStart = true
        return seconds
      } else {
        if (!isRunning) {
          animation.start()
          isRunning = true
        }
        return animation.step(seconds)
      }
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
        var remainingSeconds = seconds
        animations.forEach { remainingSeconds = Math.min(remainingSeconds, it.step(seconds)) }
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
        val runner = animations[index]
        if (needsInit) {
          runner.start()
          needsInit = false
        }
        remainingSeconds = runner.step(remainingSeconds)
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

fun Animation.runWhileNotZero(condition: () -> Boolean): Animation =
  object : Animation {
    override fun start() {
      this@runWhileNotZero.start()
    }

    override fun step(seconds: Double): Double =
      if (condition()) this@runWhileNotZero.step(seconds) else seconds
  }

fun Animation.startWhenNotZero(intEvaluator: IntEvaluator) =
  object : Animation {
    var isRunning = false

    override fun start() {
      isRunning = false
    }

    override fun step(seconds: Double): Double {
      if (intEvaluator.eval() != 0) {
        this@startWhenNotZero.start()
        isRunning = true
      }

      if (isRunning) {
        this@startWhenNotZero.step(seconds)
      }

      return 0.0
    }
  }

class SelectAnimation(val indexEvaluator: IntEvaluator, val animations: Array<Animation>) : Animation {
  var selectedIndex = 0

  override fun start() {
    selectedIndex = indexEvaluator.eval()
    animations[selectedIndex].start()
  }

  override fun step(seconds: Double): Double {
    selectedIndex = indexEvaluator.eval()
    return animations[selectedIndex].step(seconds)
  }
}
