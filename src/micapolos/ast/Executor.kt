package micapolos.ast

import micapolos.Leo.leo
import micapolos.tata8.Composite
import micapolos.tata8.Game
import micapolos.tata8.Image
import micapolos.tata8.Shader
import java.util.*
import kotlin.reflect.KClass

internal interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}

internal data class State(var value: Any? = null)

internal val Any?.leoString get() =
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
        is Expression.Constant<*> -> {
          object : Runner {
            override fun init() {
              state.value = expression.value
            }
          }
        }

        is Expression.Variable<*> -> {
          val initializerState = state(expression.initializer)
          object : Runner {
            override fun init() {
              state.value = initializerState.value
            }
          }
        }

        is Expression.Set<*> -> {
          val lhsState = state(expression.lhs)
          val rhsState = state(expression.rhs)
          object : Runner {
            override fun step(seconds: Float): Float {
              lhsState.value = rhsState.value
              return seconds
            }
          }
        }

        is Expression.Application<*> -> {
          val argStates = expression.args.map { state(it) }
          when (expression.name) {
            "logged" -> object : Runner {
              override fun step(seconds: Float): Float {
                when (argStates.size) {
                  1 -> {
                    state.value = argStates[0].value
                    Game.log(argStates[0].value.leoString)
                  }
                  2 -> {
                    state.value = argStates[1].value
                    Game.log(leo(argStates[0].value as String, argStates[1].value.leoString))
                  }
                }
                return seconds
              }
            }

            "readOnly" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value
                return seconds
              }
            }

            "Int.plus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int + argStates[1].value as Int
                return seconds
              }
            }

            "Double.plus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Double + argStates[1].value as Double
                return seconds
              }
            }

            "Int.minus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int - argStates[1].value as Int
                return seconds
              }
            }

            "Double.minus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Double - argStates[1].value as Double
                return seconds
              }
            }

            "Int.times" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int * argStates[1].value as Int
                return seconds
              }
            }

            "Double.times" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Double * argStates[1].value as Double
                return seconds
              }
            }

            "sequence" -> object : Runner {
              override fun step(seconds: Float): Float {
                return seconds
              }
            }

            "Int.keepAdding" -> object : Runner {
              override fun step(seconds: Float): Float {
                argStates[0].value = argStates[0].value as Int + argStates[1].value as Int
                return seconds
              }
            }

            "Double.keepAdding" -> object : Runner {
              override fun step(seconds: Float): Float {
                argStates[0].value = argStates[0].value as Double + argStates[1].value as Double * seconds
                return seconds
              }
            }

            "loadImage" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = Game.loadImage(
                  (argStates[0].value as KClass<*>).java,
                  argStates[1].value as String
                )
                return seconds
              }
            }

            "sprite" -> object : Runner {
              override fun step(seconds: Float): Float {
                Game.background.canvas.draw(
                  argStates[0].value as Image,
                  (argStates[1].value as Double).toFloat(),
                  (argStates[2].value as Double).toFloat(),
                  (argStates[3].value as Double).toFloat(),
                  (argStates[4].value as Double).toFloat(),
                  argStates[5].value as Boolean,
                  argStates[6].value as Boolean,
                  (argStates[7].value as Double).toFloat(),
                  (argStates[8].value as Double).toFloat(),
                  argStates[9].value as Composite,
                  (argStates[10].value as Double).toFloat()
                )
                return seconds
              }
            }

            else -> error("Unsupported function: ${expression.name}")
          }
        }
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
