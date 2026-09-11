package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Color
import micapolos.zexy3.runtime.*
import micapolos.tata8.Color as TataColor

fun Compiler.animatedColor(color: Color): Animated<TataColor> =
  Animated(colorEvaluator(color), colorAnimation(color))

fun Compiler.colorEvaluator(color: Color): ObjectEvaluator<TataColor> =
  when (color) {
    is Color.Rgba -> {
      var previousRed = 0.0
      var previousGreen = 0.0
      var previousBlue = 0.0
      var previousAlpha = 0.0
      var previousColor: TataColor? = null
      val redEvaluator = evaluator(color.red) as DoubleEvaluator
      val greenEvaluator = evaluator(color.green) as DoubleEvaluator
      val blueEvaluator = evaluator(color.blue) as DoubleEvaluator
      val alphaEvaluator = evaluator(color.alpha) as DoubleEvaluator
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
      }
    }
  }

fun Compiler.colorAnimation(color: Color): Animation = noAnimation