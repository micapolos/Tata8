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
        is Live.Constant<*> -> constantRunner(state, live.value)

        is Live.Variable<*> -> variableRunner(state, state(live.initializer))

        is Live.Init<*> -> initRunner(state(live.lhs), state(live.rhs))

        is Live.Set<*> -> setRunner(state(live.lhs), state(live.rhs))

        is Live.Elastic -> elasticRunner(state, state(live.target))

        is Live.Conditional<*> -> conditionalRunner(state,
          state(live.condition),
          expression(live.trueLive),
          expression(live.falseLive))

        is Live.Application<*> -> applicationRunner(live.primitive, state, live.args.map(::state), ::state)
        is Live.Bottom -> bottomRunner

        is Live.Pause -> {
          val secondsState = state(live.seconds)
          sleepRunner { (secondsState.value as Double).toFloat() }
        }

        is Live.Block -> sequence(live.lives.map { expression(it).runner })

        is Live.DoWhile -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).runner
          doWhileRunner(bodyRunner) { conditionState.value as Boolean }
        }

        is Live.ConditionalStep -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).runner
          stepRunner({ conditionState.value as Boolean }, bodyRunner)
        }

        is Live.ConditionalInit -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).runner
          initRunner({ conditionState.value as Boolean }, bodyRunner)
        }
      }
    }

  fun expression(live: Live<*>): Expression {
    val executor = Executor(states)
    val state = executor.state(live)
    return Expression(state, executor.runner)
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