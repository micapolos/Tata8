package micapolos.zexy2.live

interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}

fun sleepRunner(getSeconds: () -> Float) =
  object : Runner {
    var remainingSeconds: Float = 0f

    override fun init() {
      remainingSeconds = getSeconds()
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
      var remainingSeconds = Float.POSITIVE_INFINITY
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
          return seconds
        } else {
          val runner = runners[index]
          remainingSeconds = runner.step(remainingSeconds)
          if (seconds == 0f) {
            return 0f
          } else {
            index++
            needsInit = true
          }
        }
      }
    }
  }