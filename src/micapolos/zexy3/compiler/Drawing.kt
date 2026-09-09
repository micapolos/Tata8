package micapolos.zexy3.compiler

import micapolos.Sandbox
import micapolos.tata8.Game
import micapolos.zexy.Drawable
import micapolos.zexy3.indexed.*
import micapolos.zexy3.runtime.Animation
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

fun Compiler.evaluator(drawing: Drawing): ObjectEvaluator<Drawable> =
  when (drawing) {
    Drawing.Empty -> ObjectEvaluator { Drawable {} }

    is Drawing.Rect -> ObjectEvaluator {
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      val widthEvaluator = intEvaluator(drawing.width)
      val heightEvaluator = intEvaluator(drawing.height)
      Drawable { canvas ->
        canvas.drawRect(xEvaluator.eval(), yEvaluator.eval(), widthEvaluator.eval(), heightEvaluator.eval())
      }
    }

    is Drawing.Sprite -> {
      val imageEvaluator = evaluator(drawing.image as Image)
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      ObjectEvaluator {
        Drawable { canvas ->
          canvas.draw(imageEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
        }
      }
    }

    is Drawing.Label -> {
      val textEvaluator = evaluator(drawing.text as Text)
      val xEvaluator = intEvaluator(drawing.x)
      val yEvaluator = intEvaluator(drawing.y)
      ObjectEvaluator {
        Drawable { canvas ->
          canvas.draw(textEvaluator.eval(), xEvaluator.eval(), yEvaluator.eval())
        }
      }
    }

    is Drawing.Stack -> {
      val drawings = drawing.drawings.map { evaluator(it as Drawing) }
      ObjectEvaluator {
        Drawable { canvas ->
          drawings.forEach { drawing ->
            drawing.eval().drawOn(canvas)
          }
        }
      }
    }

    is Drawing.WithColor -> TODO()

    is Drawing.WithFont -> {
      val drawingEvaluator = evaluator(drawing.drawing as Drawing)
      val fontEvaluator = evaluator(drawing.font as Font)
      ObjectEvaluator {
        Drawable { canvas ->
          val previousFont = canvas.font
          canvas.font = fontEvaluator.eval()
          drawingEvaluator.eval().drawOn(canvas)
          canvas.font = previousFont
        }
      }
    }

    is Drawing.WithComposite -> {
      val drawingEvaluator = evaluator(drawing.drawing as Drawing)
      val composite = drawing.composite.tata8
      ObjectEvaluator {
        Drawable { canvas ->
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
    .evaluator(drawing)
    .eval()
    .drawOn(Game.background.canvas)
  Game.start()
}