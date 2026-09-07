package micapolos.zexy2.live

interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}

fun pauseRunner(getSeconds: () -> Float) =
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

fun doWhileRunner(body: Runner, condition: () -> Boolean) =
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
        } else if (condition()) {
          needsInit = true
        } else {
          done = true
        }
      }
    }
  }