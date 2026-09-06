package micapolos.zexy2.ast

import micapolos.Leo.leo
import micapolos.tata8.*
import micapolos.zexy2.Key
import kotlin.reflect.KClass

typealias LiveState = (Live<*>) -> State

fun <T> Live.Constant<T>.runner(state: State) =
  object : Runner {
    override fun init() {
      state.value = value
    }
  }

fun <T> Live.Variable<T>.runner(state: State, liveState: LiveState) =
  object : Runner {
    val initializerState = liveState(initializer)
    override fun init() {
      state.value = initializerState.value
    }
  }

fun <T> Live.Set<T>.runner(liveState: LiveState) =
  object : Runner {
    val lhsState = liveState(lhs)
    val rhsState = liveState(rhs)
    override fun step(seconds: Float): Float {
      lhsState.value = rhsState.value
      return seconds
    }
  }

fun <T> Live.Conditional<T>.runner(state: State, liveState: LiveState) =
  object : Runner {
    val conditionState = liveState(condition)
    val trueState = liveState(trueLive)
    val falseState = liveState(falseLive)
    override fun step(seconds: Float): Float {
      state.value = if (conditionState.value as Boolean) {
        trueState.value
      } else {
        falseState.value
      }
      return seconds
    }
  }

fun <T> Live.Application<T>.runner(state: State, liveState: LiveState): Runner {
  return when (name) {
    "logged" -> object : Runner {
      val argStates = args.map { liveState(it) }
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
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value
        return seconds
      }
    }

    "Int.plus" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    "Double.plus" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double + argStates[1].value as Double
        return seconds
      }
    }

    "Int.minus" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int - argStates[1].value as Int
        return seconds
      }
    }

    "Double.minus" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double - argStates[1].value as Double
        return seconds
      }
    }

    "Int.times" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int * argStates[1].value as Int
        return seconds
      }
    }

    "Double.times" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double * argStates[1].value as Double
        return seconds
      }
    }

    "parallel" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        return seconds
      }
    }

//    "sequence" -> object : Runner {
//      override fun step(seconds: Float): Float {
//        return seconds
//      }
//    }

    "Int.keepAdding" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        argStates[0].value = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    "Double.keepAdding" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        argStates[0].value = argStates[0].value as Double + argStates[1].value as Double * seconds
        return seconds
      }
    }

    "loadImage" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = Game.loadImage(
          (argStates[0].value as KClass<*>).java,
          argStates[1].value as String
        )
        return seconds
      }
    }

    "sprite" -> object : Runner {
      val argStates = args.map { liveState(it) }
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

    "label" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        Game.background.canvas.draw(
          argStates[0].value as String,
          (argStates[1].value as Double).toInt(),
          (argStates[2].value as Double).toInt(),
          argStates[3].value as Color,
          (argStates[4].value as Font?) ?: Game.font)
        return seconds
      }
    }

    "Key.isPressed" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.isPressed
        return seconds
      }
    }

    "Key.pressed" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.pressed()
        return seconds
      }
    }

    "Key.released" -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.released()
        return seconds
      }
    }

    "Mouse.position.x" -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.position.x.toDouble()
        return seconds
      }
    }

    "Mouse.position.y" -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.position.y.toDouble()
        return seconds
      }
    }

    "Mouse.button.isPressed" -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.button.isPressed()
        return seconds
      }
    }

    "Mouse.button.pressed" -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.button.didPress()
        return seconds
      }
    }

    else -> error("Unsupported function: ${name}")
  }
}
