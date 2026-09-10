package micapolos.zexy3.compiler

import micapolos.Sandbox
import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.runtime.*
import micapolos.zexy3.runtime.Drawing as RuntimeDrawing

fun Compiler.animatedDrawing(drawing: Drawing): Animated<RuntimeDrawing> =
  Animated(drawingEvaluator(drawing), drawingAnimation(drawing))

fun Compiler.drawingAnimation(drawing: Drawing): Animation =
  when (drawing) {
    Drawing.Empty ->
      noAnimation
    is Drawing.Rect ->
      parallel(
        valueAnimation(drawing.x),
        valueAnimation(drawing.y),
        valueAnimation(drawing.width),
        valueAnimation(drawing.height))
    is Drawing.Sprite ->
      parallel(
        valueAnimation(drawing.image),
        valueAnimation(drawing.x),
        valueAnimation(drawing.y))
    is Drawing.Label ->
      parallel(
        valueAnimation(drawing.text),
        valueAnimation(drawing.x),
        valueAnimation(drawing.y))
    is Drawing.Stack ->
      parallel(drawing.drawings.map { valueAnimation(it) })
    is Drawing.WithComposite ->
      valueAnimation(drawing.drawing)
    is Drawing.WithColor ->
      parallel(
        valueAnimation(drawing.drawing),
        valueAnimation(drawing.color))
    is Drawing.WithFont ->
      parallel(
        valueAnimation(drawing.drawing),
        valueAnimation(drawing.font))
  }

fun Compiler.drawingEvaluator(drawing: Drawing): ObjectEvaluator<RuntimeDrawing> =
  when (drawing) {
    Drawing.Empty -> ObjectEvaluator { RuntimeDrawing {} }

    is Drawing.Rect -> ObjectEvaluator {
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      val widthEvaluator = intEvaluator(drawing.width)
      val heightEvaluator = intEvaluator(drawing.height)
      RuntimeDrawing { canvas ->
        canvas.drawRect(xEvaluator.eval(), yEvaluator.eval(), widthEvaluator.eval(), heightEvaluator.eval())
      }
    }

    is Drawing.Sprite -> {
      val imageEvaluator = imageEvaluator(drawing.image as Image)
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      ObjectEvaluator {
        RuntimeDrawing { canvas ->
          canvas.draw(imageEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
        }
      }
    }

    is Drawing.Label -> {
      val textEvaluator = textEvaluator(drawing.text as Text)
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      ObjectEvaluator {
        RuntimeDrawing { canvas ->
          canvas.draw(textEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
        }
      }
    }

    is Drawing.Stack -> {
      val drawings = drawing.drawings.map { drawingEvaluator(it as Drawing) }
      ObjectEvaluator {
        RuntimeDrawing { canvas ->
          drawings.forEach { drawing ->
            drawing.eval().drawOn(canvas)
          }
        }
      }
    }

    is Drawing.WithColor -> TODO()

    is Drawing.WithFont -> {
      val drawingEvaluator = drawingEvaluator(drawing.drawing as Drawing)
      val fontEvaluator = fontEvaluator(drawing.font as Font)
      ObjectEvaluator {
        RuntimeDrawing { canvas ->
          val previousFont = canvas.font
          canvas.font = fontEvaluator.eval()
          drawingEvaluator.eval().drawOn(canvas)
          canvas.font = previousFont
        }
      }
    }

    is Drawing.WithComposite -> {
      val drawingEvaluator = drawingEvaluator(drawing.drawing as Drawing)
      val composite = drawing.composite.tata8
      ObjectEvaluator {
        RuntimeDrawing { canvas ->
          val previousComposite = canvas.composite
          canvas.composite = composite
          drawingEvaluator.eval().drawOn(canvas)
          canvas.composite = previousComposite
        }
      }
    }
  }

fun main() {
  val drawing = Drawing.Sprite(
    Image.Resource("quote.png"),
    Integer.Constant(10),
    Integer.Constant(10))
  Compiler(Sandbox::class)
    .drawingEvaluator(drawing)
    .eval()
    .drawOn(Game.background.canvas)
  Game.start()
}