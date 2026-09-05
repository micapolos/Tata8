package micapolos.ast

import micapolos.tata8.Game

interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}

data class State(var value: Any? = null)

class Executor {
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
            "Int.plus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int + argStates[1].value as Int
                return seconds
              }
            }

            "Int.minus" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int - argStates[1].value as Int
                return seconds
              }
            }

            "Int.times" -> object : Runner {
              override fun step(seconds: Float): Float {
                state.value = argStates[0].value as Int * argStates[1].value as Int
                return seconds
              }
            }

            "sequence" -> object : Runner {
              override fun step(seconds: Float): Float {
                return seconds
              }
            }

            "fillRect" -> object : Runner {
              override fun step(seconds: Float): Float {
                Game.background.canvas.fillRect(
                  argStates[0].value as Int,
                  argStates[1].value as Int,
                  argStates[2].value as Int,
                  argStates[3].value as Int
                )
                return seconds
              }
            }

            "Int.keepAdding" -> object : Runner {
              override fun step(seconds: Float): Float {
                argStates[0].value = argStates[0].value as Int + argStates[1].value as Int
                return seconds
              }
            }

            else -> TODO()
          }
        }
      }
    }
  }

fun Expression<*>.show() {
  var executor = Executor()
  executor.state(this)
  executor.runners.forEach { it.init() }
  Game.onUpdate = {
    Game.background.canvas.clear()
    executor.runners.forEach { it.step(1 / 60f) }
  }
  Game.start()
}
