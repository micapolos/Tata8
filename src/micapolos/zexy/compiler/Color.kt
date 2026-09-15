package micapolos.zexy.compiler

import micapolos.zexy.indexed.Color
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.instantAnimation
import micapolos.tata8.Color as TataColor

fun Compiler.animatedColor(color: Color): Animated<TataColor> =
  Animated(colorEvaluator(color), ObjectEvaluator { instantAnimation })

fun Compiler.colorEvaluator(color: Color): Evaluator<TataColor> =
  when (color) {
    is Color.Rgba -> {
      var previousRed = 0.0
      var previousGreen = 0.0
      var previousBlue = 0.0
      var previousAlpha = 0.0
      var previousColor: TataColor? = null

      val redEvaluator = doubleEvaluator(color.red)
      val greenEvaluator = doubleEvaluator(color.green)
      val blueEvaluator = doubleEvaluator(color.blue)
      val alphaEvaluator = doubleEvaluator(color.alpha)
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
