package micapolos.zexy2.live

import micapolos.Leo.leo
import micapolos.tata8.Game
import micapolos.tata8.Math.elastic

interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}

fun sleepRunner(secondsState: State<Double>) =
  object : Runner {
    var remainingSeconds: Float = 0f

    override fun init() {
      remainingSeconds = secondsState.value.toFloat()
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

fun parallel(runner: Runner, vararg runners: Runner) =
  parallel(listOf(runner, *runners))

fun parallel(runners: List<Runner>): Runner =
  object : Runner {
    override fun init() {
      runners.forEach(Runner::init)
    }

    override fun step(seconds: Float): Float {
      var remainingSeconds = seconds
      runners.forEach { remainingSeconds = Math.min(remainingSeconds, it.step(seconds)) }
      return remainingSeconds
    }
  }

fun sequence(runner: Runner, vararg runners: Runner) =
  sequence(listOf(runner, *runners))

fun sequence(runners: List<Runner>): Runner =
  object : Runner {
    var index = 0
    var needsInit = false

    override fun init() {
      index = 0
      needsInit = true
    }

    override fun step(seconds: Float): Float {
      var remainingSeconds = seconds
      while (true) {
        if (index == runners.size) {
          return remainingSeconds
        } else {
          val runner = runners[index]
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

fun doWhileRunner(body: Runner, conditionState: State<Boolean>) =
  object : Runner {
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

fun stepRunner(conditionState: State<Boolean>, runner: Runner) =
  object : Runner {
    override fun init() {
      runner.init()
    }

    override fun step(seconds: Float): Float =
      if (conditionState.value) runner.step(seconds) else 0f
  }

fun initRunner(startState: State<Boolean>, runner: Runner) =
  object : Runner {
    var isRunning = false

    override fun init() {
      isRunning = false
    }

    override fun step(seconds: Float): Float {
      if (startState.value) {
        runner.init()
        isRunning = true
      }

      if (isRunning) {
        return runner.step(seconds)
      } else {
        return 0f
      }

    }
  }

fun elasticRunner(current: State<Double>, target: State<Double>) =
  object : Runner {
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

fun frameTimeRunner(outState: State<Double>) =
  object : Runner {
    override fun init() {
      outState.value = 0.0
    }

    override fun step(seconds: Float): Float {
      outState.value = seconds.toDouble();
      return seconds
    }
  }

fun <T> loggedRunner(outState: State<T>, state: State<T>) = object : Runner {
  override fun step(seconds: Float): Float {
    outState.value = state.value
    Game.log(state.value.leoString)
    return seconds
  }
}

fun <T> loggedAsRunner(outState: State<T>, state: State<T>, string: State<String>) = object : Runner {
  override fun step(seconds: Float): Float {
    outState.value = state.value
    Game.log(string.value, state.value.leoString)
    return seconds
  }
}

fun <T, V> applyRunner(resultState: State<T>, state: State<V>, fn: (V) -> T) =
  object : Runner {
    override fun step(seconds: Float): Float {
      resultState.internalValue = fn(state.value)
      return seconds
    }
  }

fun <T, V1, V2> applyRunner(resultState: State<T>, state1: State<V1>, state2: State<V2>, fn: (V1, V2) -> T) =
  object : Runner {
    override fun step(seconds: Float): Float {
      resultState.internalValue = fn(state1.value, state2.value)
      return seconds
    }
  }

fun <T> readonlyRunner(outState: State<T>, state: State<T>) =
  applyRunner(outState, state) { it }

fun booleanNotRunner(resultState: State<Boolean>, state: State<Boolean>) =
  object : Runner {
    override fun step(seconds: Float): Float {
      resultState.internalValue = !state.value
      return seconds
    }
  }

