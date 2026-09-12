package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Game
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.Drawing
import micapolos.zexy3.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame {
  val animatedDrawing = animated(game.drawing) as Animated<Drawing>
  val drawingAnimation = animatedDrawing.animation
  val initialIntArray = intArray.copyOf()
  val initialDoubleArray = doubleArray.copyOf()
  val initialObjectArray = objectArray.copyOf()
  val initialAnimatedValues = animatedValues.toList()
  return RuntimeGame(
    game.title,
    Animated(
      animatedDrawing.evaluator,
      object : Animation {
        override fun start() {
          for (i in animatedValues.indices) {
            animatedValues[i] = initialAnimatedValues[i]
          }
          drawingAnimation.start()
        }

        override fun step(seconds: Double): Double {
          return drawingAnimation.step(seconds)
        }
      })
  )
}