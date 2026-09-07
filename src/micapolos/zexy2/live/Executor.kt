package micapolos.zexy2.live

import micapolos.tata8.Game
import micapolos.tata8.Shader
import micapolos.zexy2.parallel
import java.util.*

internal val Any?.leoString
  get() =
    when (this) {
      is Double -> String.format(Locale.ROOT, "%.3f", this)
      is String -> "\"$this\""
      else -> "$this"
    }

internal class Executor(
  val states: MutableMap<Live<*>, State> = mutableMapOf(),
  val runners: MutableList<Runner> = mutableListOf(),
) {
  fun state(live: Live<*>): State =
    states[live] ?: State().also { state ->
      states[live] = state

      runners += when (live) {
        is Live.Constant<*> -> live.runner(state)

        is Live.Variable<*> -> live.runner(state, ::state)

        is Live.Set<*> -> live.runner(::state)

        is Live.Conditional<*> -> {
          val conditionState = state(live.condition)
          val (trueState, trueRunner) = childStateAndRunner(live.trueLive)
          val (falseState, falseRunner) = childStateAndRunner(live.falseLive)

          object : Runner {
            override fun init() {
              trueRunner.init()
              falseRunner.init()
            }

            override fun step(seconds: Float): Float {
              state.value =
                if (conditionState.value as Boolean) {
                  trueRunner.step(seconds)
                  trueState.value
                } else {
                  falseRunner.step(seconds)
                  falseState.value
                }
              return seconds
            }
          }
        }

        is Live.Application<*> -> live.runner(state, ::state)
        is Live.Bottom -> bottomRunner

        is Live.Pause -> {
          val secondsState = state(live.seconds)
          sleepRunner { (secondsState.value as Double).toFloat() }
        }

        is Live.Block -> sequence(live.lives.map { childStateAndRunner(it).second })

        is Live.DoWhile -> {
          val conditionState = state(live.condition)
          val bodyRunner = childStateAndRunner(live.body).second
          doWhileRunner(bodyRunner) { conditionState.value as Boolean }
        }

        is Live.ConditionalStep -> {
          val conditionState = state(live.condition)
          val bodyRunner = childStateAndRunner(live.body).second
          stepRunner({ conditionState.value as Boolean }, bodyRunner)
        }

        is Live.ConditionalInit -> {
          val conditionState = state(live.condition)
          val bodyRunner = childStateAndRunner(live.body).second
          initRunner({ conditionState.value as Boolean }, bodyRunner)
        }
      }
    }

  fun <T> childStateAndRunner(live: Live<T>): Pair<State, Runner> {
    val executor = Executor(states)
    val state = executor.state(live)
    return state to executor.runner
  }

  val runner get() = parallel(runners)
}

fun Live<*>.show() {
  val executor = Executor()
  executor.state(this)
  val runner = executor.runner

  Game.screen.shader = Shader.CRT_PHOSPHOR
  runner.init()
  Game.onStep = { seconds ->
    if (Game.keys.reset.pressed()) {
      runner.init()
    }
    Game.background.canvas.clear()
    runner.step(seconds)
  }
  Game.start()
}

fun show(live: Live<*>, vararg lives: Live<*>) {
  parallel(live, *lives).show()
}

fun main() {
  val condition = Live.Variable(Live.Constant(Boolean::class, true))
  val trueConstant = Live.Constant(Integer::class, 10)
  val falseConstant = Live.Bottom
  val conditional =
    Live.Conditional(
      Integer::class,
      condition,
      trueConstant,
      falseConstant)
  val executor = Executor()
  val conditionState = executor.state(condition)
  val state = executor.state(conditional)
  val runner = executor.runner
  runner.init()
  runner.step(1f)

  IO.println(state)

  conditionState.value = false
  try {
    runner.step(1f)
    throw AssertionError("Should throw")
  } catch (e: IllegalStateException) {
    // OK
  }
}