package micapolos.zexy2.live

import micapolos.Leo.leo
import micapolos.tata8.*
import micapolos.tata8.Math.lerp
import micapolos.zexy.ParallaxRatio.applyParallaxRatio
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
  return when (primitive) {
    Primitive.LOGGED -> object : Runner {
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

    Primitive.READONLY -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value
        return seconds
      }
    }

    Primitive.BOOLEAN_NOT -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = !(argStates[0].value as Boolean)
        return seconds
      }
    }

    Primitive.BOOLEAN_AND -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Boolean and argStates[1].value as Boolean
        return seconds
      }
    }

    Primitive.BOOLEAN_OR -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Boolean or argStates[1].value as Boolean
        return seconds
      }
    }

    Primitive.INT_PLUS -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_PLUS -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double + argStates[1].value as Double
        return seconds
      }
    }

    Primitive.INT_MINUS -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int - argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_MINUS -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double - argStates[1].value as Double
        return seconds
      }
    }

    Primitive.INT_TIMES -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Int * argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_TIMES -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = argStates[0].value as Double * argStates[1].value as Double
        return seconds
      }
    }

    Primitive.PARALLEL -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        return seconds
      }
    }

    Primitive.INT_KEEP_ADDING -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        argStates[0].value = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_KEEP_ADDING -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        argStates[0].value = argStates[0].value as Double + argStates[1].value as Double * seconds
        return seconds
      }
    }

    Primitive.LOAD_IMAGE -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = Game.loadImage(
          (argStates[0].value as KClass<*>).java,
          argStates[1].value as String
        )
        return seconds
      }
    }

    Primitive.SPRITE -> object : Runner {
      val argStates = args.map { liveState(it) }
      var screenWidthState = liveState(micapolos.zexy2.Screen.size.width)
      var screenHeightState = liveState(micapolos.zexy2.Screen.size.height)
      var cameraPositionXState = liveState(micapolos.zexy2.Camera.position.x)
      var cameraPositionYState = liveState(micapolos.zexy2.Camera.position.x)
      var cameraScreenAlignmentXState = liveState(micapolos.zexy2.Camera.screenAlignment.x)
      var cameraScreenAlignmentYState = liveState(micapolos.zexy2.Camera.screenAlignment.y)

      override fun step(seconds: Float): Float {
        (argStates[0].value as Image?)?.let { image ->
          val alignmentX = (argStates[1].value as Double).toFloat()
          val alignmentY = (argStates[2].value as Double).toFloat()
          val positionX = argStates[3].value as Double
          val positionY = argStates[4].value as Double
          val imageWidth = image.size.width.toFloat()
          val imageHeight = image.size.height.toFloat()
          val anchorX = lerp(0f, imageWidth, alignmentX)
          val anchorY = lerp(0f, imageHeight, alignmentY)
          val screenWidth = (screenWidthState.value as Double).toFloat()
          val screenHeight = (screenHeightState.value as Double).toFloat()
          val cameraPositionX = cameraPositionXState.value as Double
          val cameraPositionY = cameraPositionYState.value as Double
          val cameraScreenAlignmentX = cameraScreenAlignmentXState.value as Double
          val cameraScreenAlignmentY = cameraScreenAlignmentYState.value as Double
          val cameraAnchorX = lerp(0f, screenWidth, cameraScreenAlignmentX.toFloat()).toDouble()
          val cameraAnchorY = lerp(0f, screenHeight, cameraScreenAlignmentY.toFloat()).toDouble()
          val parallaxRatio = argStates[11].value as Double

          val drawX = applyParallaxRatio(
            positionX,
            cameraAnchorX,
            cameraPositionX,
            parallaxRatio
          ).toFloat()
          val drawY =
            applyParallaxRatio(
              positionY,
              cameraAnchorY,
              cameraPositionY,
              1.0
            ).toFloat()

          Game.background.canvas.draw(
            image,
            anchorX,
            anchorY,
            drawX, drawY,
            argStates[5].value as Boolean,
            argStates[6].value as Boolean,
            (argStates[7].value as Double).toFloat(),
            (argStates[8].value as Double).toFloat(),
            argStates[9].value as Composite,
            (argStates[10].value as Double).toFloat()
          )
        }
        return seconds
      }
    }

    Primitive.LABEL -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        val string = argStates[0].value as String
        val alignmentX = (argStates[1].value as Double).toFloat()
        val alignmentY = (argStates[2].value as Double).toFloat()
        val positionX = (argStates[3].value as Double).toFloat()
        val positionY = (argStates[4].value as Double).toFloat()
        val color = argStates[5].value as Color
        val font = argStates[6].value as Font? ?: Game.font
        val drawX = positionX - lerp(0f, font.width(string).toFloat(), alignmentX)
        val drawY = positionY - lerp(0f, font.height.toFloat(), alignmentY)
        Game.background.canvas.draw(string, drawX.toInt(), drawY.toInt(), color, font)
        return seconds
      }
    }

    Primitive.KEY_IS_PRESSED -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.isPressed
        return seconds
      }
    }

    Primitive.KEY_PRESSED -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.pressed()
        return seconds
      }
    }

    Primitive.KEY_RELEASED -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Key).tata8.released()
        return seconds
      }
    }

    Primitive.MOUSE_POSITION_X -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.position.x.toDouble()
        return seconds
      }
    }

    Primitive.MOUSE_POSITION_Y -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.position.y.toDouble()
        return seconds
      }
    }

    Primitive.MOUSE_BUTTON_IS_PRESSED -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.button.isPressed()
        return seconds
      }
    }

    Primitive.MOUSE_BUTTON_PRESSED -> object : Runner {
      override fun step(seconds: Float): Float {
        state.value = Game.mouse.button.didPress()
        return seconds
      }
    }

    Primitive.IMAGE_WIDTH -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Image).size.width.toDouble()
        return seconds
      }
    }

    Primitive.IMAGE_HEIGHT -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Image).size.height.toDouble()
        return seconds
      }
    }

    Primitive.FONT_STRING_WIDTH -> object : Runner {
      val argStates = args.map { liveState(it) }
      override fun step(seconds: Float): Float {
        state.value = (argStates[0].value as Font).width(argStates[1].value as String).toDouble()
        return seconds
      }
    }
  }
}
