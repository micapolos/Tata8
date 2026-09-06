package micapolos.zexy2.ast

import micapolos.tata8.Game
import micapolos.tata8.Shader
import micapolos.zexy2.Animation
import java.util.*

internal val Any?.leoString
  get() =
    when (this) {
      is Double -> String.format(Locale.ROOT, "%.3f", this)
      is String -> "\"$this\""
      else -> "$this"
    }

internal class Executor {
  val runners = mutableListOf<Runner>()
  val states = mutableMapOf<Expression<*>, State>()

  fun state(expression: Expression<*>): State =
    states[expression] ?: State().also { state ->
      states[expression] = state

      runners += when (expression) {
        is Expression.Constant<*> -> expression.runner(state)
        is Expression.Variable<*> -> expression.runner(state, ::state)
        is Expression.Set<*> -> expression.runner(::state)
        is Expression.Conditional<*> -> expression.runner(state, ::state)
        is Expression.Application<*> -> expression.runner(state, ::state)
      }
    }

}

val Expression<*>.runner get() = Executor().apply { state(this@runner) }.let { parallel(it.runners) }

fun Expression<Animation<*>>.start() {
  val runner = runner
  Game.screen.shader = Shader.CRT_PHOSPHOR
  runner.init()
  Game.onStep = { seconds ->
    Game.background.canvas.clear()
    runner.step(seconds)
  }
  Game.start()
}
