package micapolos.zexy3.compiler

import micapolos.zexy.Drawable
import micapolos.zexy3.indexed.Drawing
import micapolos.zexy3.indexed.Font
import micapolos.zexy3.indexed.Image
import micapolos.zexy3.indexed.Text
import micapolos.zexy3.runtime.ObjectEvaluator

fun Compiler.compile(drawing: Drawing): ObjectEvaluator<Drawable> =
  when (drawing) {
    Drawing.Empty -> ObjectEvaluator { Drawable {} }

    Drawing.Rect -> ObjectEvaluator {
      Drawable { canvas ->
        canvas.drawRect(0, 0, 1, 1)
      }
    }

    is Drawing.Sprite -> {
      val image = compile(drawing.image as Image)
      ObjectEvaluator {
        Drawable { canvas ->
          canvas.draw(image.eval())
        }
      }
    }

    is Drawing.Label -> {
      val text = compile(drawing.text as Text)
      ObjectEvaluator {
        Drawable { canvas ->
          canvas.draw(text.eval(), 0, 0)
        }
      }
    }

    is Drawing.Stack -> {
      val drawings = drawing.drawings.map { compile(it as Drawing) }
      ObjectEvaluator {
        Drawable { canvas ->
          drawings.forEach { drawing ->
            drawing.eval().drawOn(canvas)
          }
        }
      }
    }

    is Drawing.Translate -> TODO()
    is Drawing.Scale -> TODO()
    is Drawing.Rotate -> TODO()

    is Drawing.WithColor -> TODO()

    is Drawing.WithFont -> {
      val drawingEvaluator = compile(drawing.drawing as Drawing)
      val fontEvaluator = compile(drawing.font as Font)
      ObjectEvaluator {
        Drawable { canvas ->
          val previousFont = canvas.font
          canvas.font = fontEvaluator.eval()
          drawingEvaluator.eval().drawOn(canvas)
          canvas.font = previousFont
        }
      }
    }

    is Drawing.Blend -> TODO()
  }