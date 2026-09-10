package micapolos.zexy3.runtime

import kotlin.math.min

interface Animation {
  fun start() {}
  fun step(seconds: Double) = 0.0
}

val noAnimation: Animation = object : Animation {}

val DoubleEvaluator.pause
  get() =
    object : Animation {
      var remainingSeconds = 0.0

      override fun start() {
        remainingSeconds = eval()
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
      animations.forEach { remainingSeconds = Math.min(remainingSeconds, it.step(seconds)) }
      return remainingSeconds
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

fun Animation.repeatWhileNotZero(intEvaluator: IntEvaluator): Animation =
  object : Animation {
    var needsInit = false
    var done = false

    override fun start() {
      needsInit = true
      done = false
    }

    override fun step(seconds: Double): Double {
      var remainingSeconds = seconds
      while (true) {
        if (done) {
          return remainingSeconds
        } else if (needsInit) {
          this@repeatWhileNotZero.start()
          needsInit = false
        }

        remainingSeconds = this@repeatWhileNotZero.step(remainingSeconds)
        if (remainingSeconds == 0.0) {
          return 0.0
        } else if (intEvaluator.eval() != 0) {
          needsInit = true
        } else {
          done = true
        }
      }
    }
  }

fun Animation.runWhileNotZero(intEvaluator: IntEvaluator): Animation =
  object : Animation {
    override fun start() {
      this@runWhileNotZero.start()
    }

    override fun step(seconds: Double): Double =
      if (intEvaluator.eval() != 0) this@runWhileNotZero.step(seconds) else 0.0
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
