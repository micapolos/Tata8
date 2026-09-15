package micapolos.zexy.runtime

import micapolos.tata8.*

fun interface Drawing {
  fun drawOn(canvas: Canvas)
}

fun clipEvaluator(
  drawingEvaluator: ObjectEvaluator<Drawing>,
  xEvaluator: IntEvaluator,
  yEvaluator: IntEvaluator,
  widthEvaluator: IntEvaluator,
  heightEvaluator: IntEvaluator,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      // TODO: set and restore clip
      drawingEvaluator.eval().drawOn(canvas)
    }
  }

fun pointEvaluator(
  xEvaluator: IntEvaluator,
  yEvaluator: IntEvaluator,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.drawPoint(xEvaluator.eval(), yEvaluator.eval())
    }
  }


fun lineEvaluator(
  x1Evaluator: IntEvaluator,
  y1Evaluator: IntEvaluator,
  x2Evaluator: IntEvaluator,
  y2Evaluator: IntEvaluator,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.drawLine(x1Evaluator.eval(), y1Evaluator.eval(), x2Evaluator.eval(), y2Evaluator.eval())
    }
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

fun animatedClip(
  animatedDrawing: Animated<Drawing>,
  animatedX: Animated<Int>,
  animatedY: Animated<Int>,
  animatedWidth: Animated<Int>,
  animatedHeight: Animated<Int>,
) =
  Animated(
    clipEvaluator(
      animatedDrawing.evaluator as ObjectEvaluator<Drawing>,
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator,
      animatedWidth.evaluator as IntEvaluator,
      animatedHeight.evaluator as IntEvaluator,
    ),
    ObjectEvaluator { instantAnimation }
  )

fun animatedPoint(
  animatedX: Animated<Int>,
  animatedY: Animated<Int>,
) =
  Animated(
    pointEvaluator(
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator,
    ),
    ObjectEvaluator { instantAnimation }
  )

fun animatedLine(
  animatedX1: Animated<Int>,
  animatedY1: Animated<Int>,
  animatedX2: Animated<Int>,
  animatedY2: Animated<Int>,
) =
  Animated(
    lineEvaluator(
      animatedX1.evaluator as IntEvaluator,
      animatedY1.evaluator as IntEvaluator,
      animatedX2.evaluator as IntEvaluator,
      animatedY2.evaluator as IntEvaluator,
    ),
    ObjectEvaluator { instantAnimation }
  )

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
    ObjectEvaluator { instantAnimation }
  )

fun spriteEvaluator(
  xEvaluator: IntEvaluator, yEvaluator: IntEvaluator,
  widthEvaluator: IntEvaluator, heightEvaluator: IntEvaluator,
  imageEvaluator: ObjectEvaluator<Image>,
  imageXEvaluator: IntEvaluator, imageYEvaluator: IntEvaluator,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.draw(
        imageEvaluator.eval(),
        xEvaluator.eval(), yEvaluator.eval(),
        widthEvaluator.eval(), heightEvaluator.eval(),
        imageXEvaluator.eval(), imageYEvaluator.eval()
      )
    }
  }

fun animatedSprite(
  animatedX: Animated<Int>, animatedY: Animated<Int>,
  animatedWidth: Animated<Int>, animatedHeight: Animated<Int>,
  animatedImage: Animated<Image>,
  animatedImageX: Animated<Int>, animatedImageY: Animated<Int>
) =
  Animated(
    spriteEvaluator(
      animatedX.evaluator as IntEvaluator,
      animatedY.evaluator as IntEvaluator,
      animatedWidth.evaluator as IntEvaluator,
      animatedHeight.evaluator as IntEvaluator,
      animatedImage.evaluator as ObjectEvaluator<Image>,
      animatedImageX.evaluator as IntEvaluator,
      animatedImageY.evaluator as IntEvaluator
    ),
    ObjectEvaluator { instantAnimation }
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
    ObjectEvaluator { instantAnimation }
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
    ObjectEvaluator { instantAnimation })

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
    ObjectEvaluator { instantAnimation }
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
    ObjectEvaluator { instantAnimation }
  )

fun withCompositeEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, composite: Composite) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousComposite = canvas.composite
      canvas.composite = composite
      drawingEvaluator.eval().drawOn(canvas)
      canvas.composite = previousComposite
    }
  }

fun animatedWithComposite(animatedDrawing: Animated<Drawing>, composite: Composite) =
  Animated(
    withCompositeEvaluator(
      animatedDrawing.evaluator as ObjectEvaluator<Drawing>,
      composite
    ),
    animatedDrawing.animation
  )

