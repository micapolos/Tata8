package micapolos.zexy2.ast

import micapolos.Leo.leo
import micapolos.tata8.Composite
import micapolos.tata8.Game
import micapolos.tata8.Image
import micapolos.tata8.Shader
import micapolos.zexy2.Key
import java.util.*
import kotlin.reflect.KClass

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
        is Expression.Set<*> -> expression.runner(state, ::state)
        is Expression.Conditional<*> -> expression.runner(state, ::state)
        is Expression.Application<*> -> expression.runner(state, ::state)
      }
    }
}

fun Expression<*>.show() {
  var executor = Executor()
  executor.state(this)
  Game.screen.shader = Shader.CRT_PHOSPHOR
  executor.runners.forEach { it.init() }
  Game.onStep = { seconds ->
    Game.background.canvas.clear()
    executor.runners.forEach { it.step(seconds) }
  }
  Game.start()
}
