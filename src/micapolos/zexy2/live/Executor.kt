package micapolos.zexy2.live

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
  val states = mutableMapOf<Live<*>, State>()

  fun state(live: Live<*>): State =
    states[live] ?: State().also { state ->
      states[live] = state

      runners += when (live) {
        is Live.Constant<*> -> live.runner(state)
        is Live.Variable<*> -> live.runner(state, ::state)
        is Live.Set<*> -> live.runner(::state)
        is Live.Conditional<*> -> live.runner(state, ::state)
        is Live.Application<*> -> live.runner(state, ::state)
      }
    }

  val runner get() = parallel(runners)
}

fun Live<Animation<*>>.start() {
  val executor = Executor()
  executor.state(this)
  val runner = executor.runner

  Game.screen.shader = Shader.CRT_PHOSPHOR
  runner.init()
  Game.onStep = { seconds ->
    Game.background.canvas.clear()
    runner.step(seconds)
  }
  Game.start()
}
