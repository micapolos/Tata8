package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Image
import micapolos.zexy.indexed.Integer
import micapolos.zexy.runtime.*
import micapolos.tata8.Color as TataColor
import micapolos.tata8.Font as TataFont
import micapolos.tata8.Image as TataImage
import micapolos.zexy.runtime.Drawing as RuntimeDrawing

fun Compiler.animatedDrawing(drawing: Drawing): Animated<RuntimeDrawing> =
  when (drawing) {
    Drawing.Empty ->
      Animated(
        ObjectEvaluator { RuntimeDrawing {} },
        instantAnimation
      )

    is Drawing.Label ->
      animatedLabel(
        animated(drawing.text) as Animated<String>,
        animated(drawing.x) as Animated<Int>,
        animated(drawing.y) as Animated<Int>
      )

    is Drawing.Rect ->
      animatedRect(
        animated(drawing.x) as Animated<Int>,
        animated(drawing.y) as Animated<Int>,
        animated(drawing.width) as Animated<Int>,
        animated(drawing.height) as Animated<Int>
      )

    is Drawing.Sprite ->
      animatedSprite(
        animated(drawing.x) as Animated<Int>,
        animated(drawing.y) as Animated<Int>,
        animated(drawing.width) as Animated<Int>,
        animated(drawing.height) as Animated<Int>,
        animated(drawing.image) as Animated<TataImage>,
        animated(drawing.imageX) as Animated<Int>,
        animated(drawing.imageY) as Animated<Int>,
      )

    is Drawing.Stack ->
      animatedStack(drawing.drawings.map { animated(it) as Animated<RuntimeDrawing> }.toTypedArray())

    is Drawing.WithColor ->
      animatedWithColor(
        animated(drawing.drawing) as Animated<RuntimeDrawing>,
        animated(drawing.color) as Animated<TataColor>
      )

    is Drawing.WithComposite ->
      animatedWithComposite(
        animated(drawing.drawing) as Animated<RuntimeDrawing>,
        drawing.composite.tata8
      )

    is Drawing.WithFont ->
      animatedWithFont(
        animated(drawing.drawing) as Animated<RuntimeDrawing>,
        animated(drawing.font) as Animated<TataFont>
      )
  }

fun main() {
  val drawing = Drawing.Sprite(
    Integer.Constant(10),
    Integer.Constant(10),
    Integer.Constant(32),
    Integer.Constant(32),
    Image.Resource("quote.png"),
    Integer.Constant(0),
    Integer.Constant(0),
  )
  Compiler(Drawing::class)
    .animated(drawing)
    .evaluator.let { it as ObjectEvaluator<RuntimeDrawing> }
    .eval()
    .drawOn(Game.background.canvas)
  Game.start()
}