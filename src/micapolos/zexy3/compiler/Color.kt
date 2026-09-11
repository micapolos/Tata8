package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Color
import micapolos.zexy3.runtime.*
import micapolos.tata8.Color as TataColor

fun Compiler.animatedColor(color: Color): Animated<TataColor> =
  when (color) {
    is Color.Rgba -> {
      var animatedRed = animated(color.red)
      var animatedGreen = animated(color.green)
      var animatedBlue = animated(color.blue)
      var animatedAlpha = animated(color.alpha)

      var previousRed = 0.0
      var previousGreen = 0.0
      var previousBlue = 0.0
      var previousAlpha = 0.0

      var previousColor: TataColor? = null
      val redEvaluator = animatedRed.evaluator as DoubleEvaluator
      val greenEvaluator = animatedGreen.evaluator as DoubleEvaluator
      val blueEvaluator = animatedBlue.evaluator as DoubleEvaluator
      val alphaEvaluator = animatedAlpha.evaluator as DoubleEvaluator
      Animated(
        ObjectEvaluator {
          val red = redEvaluator.eval()
          val green = greenEvaluator.eval()
          val blue = blueEvaluator.eval()
          val alpha = alphaEvaluator.eval()
          val color = previousColor
          if (color != null && red == previousRed && green == previousGreen && blue == previousBlue && alpha == previousAlpha) {
            color
          } else {
            TataColor.rgba(red.toFloat(), green.toFloat(), blue.toFloat(), alpha.toFloat()).also {
              previousRed = red
              previousGreen = green
              previousBlue = blue
              previousAlpha = alpha
              previousColor = it
            }
          }
        },
        parallel(animatedRed.animation, animatedGreen.animation, animatedBlue.animation, animatedAlpha.animation))
    }
  }

fun Compiler.colorEvaluator(color: Color): ObjectEvaluator<TataColor> = TODO()
fun Compiler.colorAnimation(color: Color): Animation = TODO()