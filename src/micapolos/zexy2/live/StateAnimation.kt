package micapolos.zexy2.live

import micapolos.tata8.*
import micapolos.tata8.Math.lerp
import micapolos.zexy.ParallaxRatio.applyParallaxRatio
import micapolos.zexy2.Key
import java.lang.Math.floorMod
import kotlin.math.floor
import kotlin.reflect.KClass

val bottomAnimation =
  object : Animation {
    override fun step(seconds: Float): Float {
      error("Bottom")
    }
  }

fun <T> constantAnimation(state: State<T>, value: T) =
  object : Animation {
    override fun init() {
      state.value = value
    }
  }

fun <T> variableAnimation(state: State<T>, initializerState: State<T>) =
  object : Animation {
    override fun init() {
      state.value = initializerState.value
    }
  }

fun <T> startOnAnimation(lhs: State<T>, rhs: State<T>) =
  object : Animation {
    override fun init() {
      lhs.value = rhs.value
    }
  }

fun <T> keepSettingAnimation(lhs: State<T>, rhs: State<T>) =
  object : Animation {
    override fun step(seconds: Float): Float {
      lhs.value = rhs.value
      return seconds
    }
  }

fun <T> conditionalAnimation(
  resultState: State<T>,
  conditionState: State<Boolean>,
  trueExpression: Expression<T>,
  falseExpression: Expression<T>
) =
  object : Animation {
    override fun init() {
      trueExpression.animation.init()
      falseExpression.animation.init()
    }

    override fun step(seconds: Float): Float {
      resultState.value =
        if (conditionState.value) {
          trueExpression.animation.step(seconds)
          trueExpression.state.value
        } else {
          falseExpression.animation.step(seconds)
          falseExpression.state.value
        }
      return seconds
    }
  }
