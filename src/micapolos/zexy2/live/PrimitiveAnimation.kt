package micapolos.zexy2.live

import micapolos.tata8.*
import micapolos.tata8.Math.lerp
import micapolos.zexy.ParallaxRatio.applyParallaxRatio
import micapolos.zexy2.Key
import java.lang.Math.floorMod
import kotlin.math.floor
import kotlin.reflect.KClass

fun <T> animation(
  primitive: Primitive,
  resultState: State<T>,
  argStates: List<State<*>>,
  globalState: (Live<*>) -> State<*>
): Animation {
  return when (primitive) {
    Primitive.FRAME_TIME -> frameTimeAnimation(resultState as State<Double>)

    Primitive.LOGGED ->
      when (argStates.size) {
        1 -> loggedAnimation(resultState, argStates[0] as State<T>)
        else -> loggedAsAnimation(resultState, argStates[1] as State<T>, argStates[0] as State<String>)
      }

    Primitive.READONLY -> readonlyAnimation(resultState, argStates[0] as State<T>)

    Primitive.BOOLEAN_NOT -> booleanNotAnimation(resultState as State<Boolean>, argStates[0] as State<Boolean>)

    Primitive.BOOLEAN_AND -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Boolean and argStates[1].value as Boolean
        return seconds
      }
    }

    Primitive.BOOLEAN_OR -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Boolean or argStates[1].value as Boolean
        return seconds
      }
    }

    Primitive.INT_PLUS -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_PLUS -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Double + argStates[1].value as Double
        return seconds
      }
    }

    Primitive.INT_MINUS -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Int - argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_MINUS -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Double - argStates[1].value as Double
        return seconds
      }
    }

    Primitive.INT_TIMES -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Int * argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_TIMES -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = argStates[0].value as Double * argStates[1].value as Double
        return seconds
      }
    }

    Primitive.INT_DOUBLE -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Int).toDouble()
        return seconds
      }
    }

    Primitive.DOUBLE_INT -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Double).toInt()
        return seconds
      }
    }

    Primitive.INT_FLOOR_MOD -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = floorMod(argStates[0].value as Int, argStates[1].value as Int)
        return seconds
      }
    }

    Primitive.DOUBLE_FRACTION -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Double).let { it - floor(it) }
        return seconds
      }
    }

    Primitive.ARRAY_GET -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Array<*>)[argStates[1].value as Int]
        return seconds
      }
    }

    Primitive.PARALLEL -> object : Animation {
      override fun step(seconds: Float): Float {
        return seconds
      }
    }

    Primitive.INT_KEEP_ADDING -> object : Animation {
      override fun step(seconds: Float): Float {
        argStates[0].internalValue = argStates[0].value as Int + argStates[1].value as Int
        return seconds
      }
    }

    Primitive.DOUBLE_KEEP_ADDING -> object : Animation {
      override fun step(seconds: Float): Float {
        argStates[0].internalValue = argStates[0].value as Double + argStates[1].value as Double * seconds
        return seconds
      }
    }

    Primitive.LOAD_IMAGE -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = Game.loadImage(
          (argStates[0].value as KClass<*>).java,
          argStates[1].value as String
        )
        return seconds
      }
    }

    Primitive.SPRITE -> object : Animation {
      var screenWidthState = globalState(micapolos.zexy2.Screen.size.width)
      var screenHeightState = globalState(micapolos.zexy2.Screen.size.height)
      var cameraPositionXState = globalState(micapolos.zexy2.Camera.position.x)
      var cameraPositionYState = globalState(micapolos.zexy2.Camera.position.x)
      var cameraScreenAlignmentXState = globalState(micapolos.zexy2.Camera.alignment.x)
      var cameraScreenAlignmentYState = globalState(micapolos.zexy2.Camera.alignment.y)

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

    Primitive.LABEL -> object : Animation {
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

    Primitive.KEY_IS_PRESSED -> object : Animation {
      override fun init() {
        resultState.internalValue = false
      }

      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Key).tata8.isPressed
        return seconds
      }
    }

    Primitive.KEY_PRESSED -> object : Animation {
      override fun init() {
        resultState.internalValue = false
      }

      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Key).tata8.pressed()
        return seconds
      }
    }

    Primitive.KEY_RELEASED -> object : Animation {
      override fun init() {
        resultState.internalValue = false
      }

      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Key).tata8.released()
        return seconds
      }
    }

    Primitive.MOUSE_POSITION_X -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = Game.mouse.position.x.toDouble()
        return seconds
      }
    }

    Primitive.MOUSE_POSITION_Y -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = Game.mouse.position.y.toDouble()
        return seconds
      }
    }

    Primitive.MOUSE_BUTTON_IS_PRESSED -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = Game.mouse.button.isPressed()
        return seconds
      }
    }

    Primitive.MOUSE_BUTTON_PRESSED -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = Game.mouse.button.didPress()
        return seconds
      }
    }

    Primitive.IMAGE_WIDTH -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Image).size.width.toDouble()
        return seconds
      }
    }

    Primitive.IMAGE_HEIGHT -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Image).size.height.toDouble()
        return seconds
      }
    }

    Primitive.FONT_STRING_WIDTH -> object : Animation {
      override fun step(seconds: Float): Float {
        resultState.internalValue = (argStates[0].value as Font).width(argStates[1].value as String).toDouble()
        return seconds
      }
    }
  }
}
