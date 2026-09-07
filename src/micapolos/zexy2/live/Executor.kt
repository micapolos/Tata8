package micapolos.zexy2.live

import micapolos.tata8.Game
import micapolos.tata8.Shader
import micapolos.zexy2.Key
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
  val states: MutableMap<Live<*>, State<*>> = mutableMapOf(),
  val animations: MutableList<Animation> = mutableListOf(),
) {
  fun <T> state(live: Live<T>): State<T> =
    ((states[live] ?: State<T>()) as State<T>).also { state ->
      states[live] = state

      animations += when (live) {
        is Live.Constant<T> -> constantRunner(state, live.value)

        is Live.Variable<T> -> variableRunner(state, state(live.initializer))

        is Live.Init<*> -> initRunner(state(live.lhs), state(live.rhs))

        is Live.Set<*> -> setRunner(state(live.lhs), state(live.rhs))

        is Live.Elastic -> elasticRunner(state as State<Double>, state(live.target))

        is Live.Conditional<T> -> conditionalRunner(state,
          state(live.condition),
          expression(live.trueLive),
          expression(live.falseLive))

        is Live.Application<T> -> applicationRunner(live.primitive, state, live.args.map(::state), ::state)
        is Live.Bottom -> bottomAnimation

        is Live.Pause -> {
          val secondsState = state(live.seconds)
          sleepRunner(secondsState)
        }

        is Live.Block -> sequence(live.lives.map { expression(it).animation })

        is Live.DoWhile -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).animation
          doWhileRunner(bodyRunner, conditionState)
        }

        is Live.ConditionalStep -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).animation
          stepRunner(conditionState, bodyRunner)
        }

        is Live.ConditionalInit -> {
          val conditionState = state(live.condition)
          val bodyRunner = expression(live.body).animation
          initRunner(conditionState, bodyRunner)
        }
      }
    }

  fun <T> expression(live: Live<T>): Expression<T> {
    val executor = Executor(states)
    val state = executor.state(live)
    return Expression(state, executor.runner)
  }

  val runner get() = parallel(animations)
}

fun Live<*>.show() {
  val executor = Executor()
  executor.state(Key.Z.press)
  executor.state(Key.Z.isPressed)
  executor.state(Key.Z.released)
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

  conditionState.value = false
  try {
    runner.step(1f)
    throw AssertionError("Should throw")
  } catch (e: IllegalStateException) {
    // OK
  }
}