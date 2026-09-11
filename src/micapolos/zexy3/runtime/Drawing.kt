package micapolos.zexy3.runtime

import micapolos.tata8.Canvas

fun interface Drawing {
  fun drawOn(canvas: Canvas)
}

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
    parallel(animatedDrawings.map { it.animation }))