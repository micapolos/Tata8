package micapolos.zexy.compiler

import micapolos.zexy.indexed.Game
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.Animation
import micapolos.zexy.runtime.Drawing
import micapolos.zexy.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame {
  val animatedDrawing = animated(game.drawing) as Animated<Drawing>
  val drawingAnimation = animatedDrawing.animation
  val initialAnimatedValues = state.animatedArray.clone()
  val animatedArray = state.animatedArray
  val initialEvaluators = state.evaluatorArray.clone()
  val evaluatorArray = state.evaluatorArray
  return RuntimeGame(
    game.title,
    Animated(
      animatedDrawing.evaluator,
      object : Animation {
        override fun start() {
          for (i in animatedArray.indices) {
            animatedArray[i] = initialAnimatedValues[i]
            evaluatorArray[i] = initialEvaluators[i]
          }
          drawingAnimation.start()
        }

        override fun step(seconds: Double): Double {
          return drawingAnimation.step(seconds)
        }
      },
    ),
    compile(game.animation)
  )
}