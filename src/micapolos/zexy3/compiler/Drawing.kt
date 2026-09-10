package micapolos.zexy3.compiler

import micapolos.Sandbox
import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.Drawing as RuntimeDrawing
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation

fun Compiler.animation(drawing: Drawing): Animation =
  when (drawing) {
    Drawing.Empty -> noAnimation
    is Drawing.Rect -> noAnimation
    is Drawing.Sprite -> noAnimation
    is Drawing.Label -> noAnimation
    is Drawing.Stack -> noAnimation
    is Drawing.WithComposite -> noAnimation
    is Drawing.WithColor -> noAnimation
    is Drawing.WithFont -> noAnimation
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