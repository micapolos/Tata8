package micapolos.zexy3.runtime

import micapolos.tata8.Canvas
import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.tata8.Image

fun interface Drawing {
  fun drawOn(canvas: Canvas)
}

fun rectEvaluator(
  xEvaluator: IntEvaluator,
  yEvaluator: IntEvaluator,
  widthEvaluator: IntEvaluator,
  heightEvaluator: IntEvaluator,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.fillRect(xEvaluator.eval(), yEvaluator.eval(), widthEvaluator.eval(), heightEvaluator.eval())
    }
  }

fun animatedRect(
  animatedX: Animated<Int>,
  animatedY: Animated<Int>,
  animatedWidth: Animated<Int>,
  animatedHeight: Animated<Int>,
) =
  Animated(
    rectEvaluator(
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator,
      animatedWidth.evaluator as IntEvaluator,
      animatedHeight.evaluator as IntEvaluator,
    ),
    parallel(
      animatedX.animation,
      animatedY.animation,
      animatedWidth.animation,
      animatedHeight.animation
    )
  )


fun spriteEvaluator(imageEvaluator: ObjectEvaluator<Image>, xEvaluator: IntEvaluator, yEvaluator: IntEvaluator) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.draw(imageEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
    }
  }

fun animatedSprite(animatedImage: Animated<Image>, animatedX: Animated<Int>, animatedY: Animated<Int>) =
  Animated(
    spriteEvaluator(
      animatedImage.evaluator as ObjectEvaluator<Image>,
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator
    ),
    parallel(animatedImage.animation, animatedX.animation, animatedY.animation)
  )

fun labelEvaluator(textEvaluator: ObjectEvaluator<String>, xEvaluator: IntEvaluator, yEvaluator: IntEvaluator) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.draw(textEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
    }
  }

fun animatedLabel(animatedText: Animated<String>, animatedX: Animated<Int>, animatedY: Animated<Int>) =
  Animated(
    labelEvaluator(
      animatedText.evaluator as ObjectEvaluator<String>,
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator
    ),
    parallel(animatedText.animation, animatedX.animation, animatedY.animation)
  )

fun stackEvaluator(drawingEvaluators: Array<ObjectEvaluator<Drawing>>) =
  ObjectEvaluator {
    Drawing { canvas ->
      drawingEvaluators.forEach {
        it.eval().drawOn(canvas)
      }
    }
  }

fun animatedStack(animatedDrawings: Array<Animated<Drawing>>): Animated<Drawing> =
  Animated(
    stackEvaluator(animatedDrawings.map { it.evaluator as ObjectEvaluator<Drawing> }.toTypedArray()),
    parallel(animatedDrawings.map { it.animation })
  )

fun withColorEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, colorEvaluator: ObjectEvaluator<Color>) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousColor = canvas.color
      canvas.color = colorEvaluator.eval()
      drawingEvaluator.eval().drawOn(canvas)
      canvas.color = previousColor
    }
  }

fun animatedWithColor(animatedDrawing: Animated<Drawing>, animatedColor: Animated<Color>) =
  Animated(
    withColorEvaluator(
      animatedDrawing.evaluator as ObjectEvaluator<Drawing>,
      animatedColor.evaluator as ObjectEvaluator<Color>
    ),
    parallel(
      animatedDrawing.animation,
      animatedColor.animation
    )
  )

fun withFontEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, fontEvaluator: ObjectEvaluator<Font>) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousFont = canvas.font
      canvas.font = fontEvaluator.eval()
      drawingEvaluator.eval().drawOn(canvas)
      canvas.font = previousFont
    }
  }

fun animatedWithFont(animatedDrawing: Animated<Drawing>, animatedFont: Animated<Font>) =
  Animated(
    withFontEvaluator(
      animatedDrawing.evaluator as ObjectEvaluator<Drawing>,
      animatedFont.evaluator as ObjectEvaluator<Font>
    ),
    parallel(
      animatedDrawing.animation,
      animatedFont.animation
    )
  )

