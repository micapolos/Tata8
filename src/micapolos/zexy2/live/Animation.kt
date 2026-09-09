package micapolos.zexy2.live

import micapolos.tata8.Game
import micapolos.tata8.Math.elastic

interface Animation {
  fun init() {}
  fun step(seconds: Float) = seconds
}

val noAnimation = object : Animation {}

fun pauseAnimation(seconds: State<Double>) =
  object : Animation {
    var remainingSeconds: Float = 0f

    override fun init() {
      remainingSeconds = seconds.value.toFloat()
    }

    override fun step(seconds: Float): Float {
      remainingSeconds -= seconds
      if (remainingSeconds >= 0) {
        return 0f
      } else {
        val leftoverSeconds = -remainingSeconds
        remainingSeconds = 0f
        return leftoverSeconds
      }
    }
  }

fun parallel(animation: Animation, vararg animations: Animation) =
  parallel(listOf(animation, *animations))

fun parallel(animations: List<Animation>): Animation =
  object : Animation {
    override fun init() {
      animations.forEach(Animation::init)
    }

    override fun step(seconds: Float): Float {
      var remainingSeconds = seconds
      animations.forEach { remainingSeconds = Math.min(remainingSeconds, it.step(seconds)) }
      return remainingSeconds
    }
  }

fun sequence(animation: Animation, vararg animations: Animation) =
  sequence(listOf(animation, *animations))

fun sequence(animations: List<Animation>): Animation =
  object : Animation {
    var index = 0
    var needsInit = false

    override fun init() {
      index = 0
      needsInit = true
    }

    override fun step(seconds: Float): Float {
      var remainingSeconds = seconds
      while (true) {
        if (index == animations.size) {
          return remainingSeconds
        } else {
          val runner = animations[index]
          if (needsInit) {
            runner.init()
            needsInit = false
          }
          remainingSeconds = runner.step(remainingSeconds)
          if (remainingSeconds == 0f) {
            return 0f
          } else {
            index++
            needsInit = true
          }
        }
      }
    }
  }

fun doWhileRunner(body: Animation, conditionState: State<Boolean>) =
  object : Animation {
    var needsInit = false
    var done = false

    override fun init() {
      needsInit = true
      done = false
    }

    override fun step(seconds: Float): Float {
      var remainingSeconds = seconds
      while (true) {
        if (done) {
          return remainingSeconds
        } else if (needsInit) {
          body.init()
          needsInit = false
        }

        remainingSeconds = body.step(remainingSeconds)
        if (remainingSeconds == 0f) {
          return 0f
        } else if (conditionState.value) {
          needsInit = true
        } else {
          done = true
        }
      }
    }
  }

fun stepRunner(conditionState: State<Boolean>, animation: Animation) =
  object : Animation {
    override fun init() {
      animation.init()
    }

    override fun step(seconds: Float): Float =
      if (conditionState.value) animation.step(seconds) else 0f
  }

fun startOnAnimation(event: State<Boolean>, animation: Animation) =
  object : Animation {
    var isRunning = false

    override fun init() {
      isRunning = false
    }

    override fun step(seconds: Float): Float {
      if (event.value) {
        animation.init()
        isRunning = true
      }

      if (isRunning) {
        animation.step(seconds)
      }

      return 0f
    }
  }

fun elasticAnimation(current: State<Double>, target: State<Double>) =
  object : Animation {
    override fun init() {
      current.value = target.value
    }

    override fun step(seconds: Float): Float {
      current.value = elastic(
        current.value.toFloat(),
        target.value.toFloat()
      ).toDouble()
      return 0f
    }
  }

fun frameTimeAnimation(out: State<Double>) =
  object : Animation {
    override fun step(seconds: Float): Float {
      out.value = seconds.toDouble()
      return 0f
    }
  }

fun <T> loggedAnimation(outState: State<T>, state: State<T>) = object : Animation {
  override fun step(seconds: Float): Float {
    outState.value = state.value
    Game.log(state.value.leoString)
    return seconds
  }
}

fun <T> loggedAsAnimation(outState: State<T>, state: State<T>, string: State<String>) = object : Animation {
  override fun step(seconds: Float): Float {
    outState.value = state.value
    Game.log(string.value, state.value.leoString)
    return seconds
  }
}

fun <T, V> applyAnimation(resultState: State<T>, state: State<V>, fn: (V) -> T) =
  object : Animation {
    override fun step(seconds: Float): Float {
      resultState.internalValue = fn(state.value)
      return seconds
    }
  }

fun <T, V1, V2> applyAnimation(resultState: State<T>, state1: State<V1>, state2: State<V2>, fn: (V1, V2) -> T) =
  object : Animation {
    override fun step(seconds: Float): Float {
      resultState.internalValue = fn(state1.value, state2.value)
      return seconds
    }
  }

fun <T> readonlyAnimation(outState: State<T>, state: State<T>) =
  applyAnimation(outState, state) { it }

fun booleanNotAnimation(resultState: State<Boolean>, state: State<Boolean>) =
  object : Animation {
    override fun step(seconds: Float): Float {
      resultState.internalValue = !state.value
      return seconds
    }
  }

