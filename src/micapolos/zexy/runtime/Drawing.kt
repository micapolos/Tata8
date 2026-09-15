package micapolos.zexy.runtime

import micapolos.tata8.*

fun interface Drawing {
  fun drawOn(canvas: Canvas)
}

fun clipEvaluator(
  drawingEvaluator: Evaluator<Drawing>,
  xEvaluator: Evaluator<Int>,
  yEvaluator: Evaluator<Int>,
  widthEvaluator: Evaluator<Int>,
  heightEvaluator: Evaluator<Int>,
) =
  ObjectEvaluator {
    Drawing { canvas ->
      // TODO: set and restore clip
      drawingEvaluator.evalObject().drawOn(canvas)
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

fun labelEvaluator(textEvaluator: ObjectEvaluator<String>, xEvaluator: IntEvaluator, yEvaluator: IntEvaluator) =
  ObjectEvaluator {
    Drawing { canvas ->
      canvas.draw(textEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
    }
  }

fun stackEvaluator(drawingEvaluators: Array<ObjectEvaluator<Drawing>>) =
  ObjectEvaluator {
    Drawing { canvas ->
      drawingEvaluators.forEach {
        it.eval().drawOn(canvas)
      }
    }
  }

fun withColorEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, colorEvaluator: ObjectEvaluator<Color>) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousColor = canvas.color
      canvas.color = colorEvaluator.eval()
      drawingEvaluator.eval().drawOn(canvas)
      canvas.color = previousColor
    }
  }

fun withFontEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, fontEvaluator: ObjectEvaluator<Font>) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousFont = canvas.font
      canvas.font = fontEvaluator.eval()
      drawingEvaluator.eval().drawOn(canvas)
      canvas.font = previousFont
    }
  }

fun withCompositeEvaluator(drawingEvaluator: ObjectEvaluator<Drawing>, composite: Composite) =
  ObjectEvaluator {
    Drawing { canvas ->
      val previousComposite = canvas.composite
      canvas.composite = composite
      drawingEvaluator.eval().drawOn(canvas)
      canvas.composite = previousComposite
    }
  }
