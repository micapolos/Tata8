package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Drawing
import micapolos.zexy.indexed.Image
import micapolos.zexy.indexed.Integer
import micapolos.zexy.runtime.*
import micapolos.zexy.runtime.Drawing as RuntimeDrawing

fun Compiler.drawingEvaluator(drawing: Drawing): Evaluator<RuntimeDrawing> =
  when (drawing) {
    Drawing.Empty ->
      ObjectEvaluator { RuntimeDrawing {} }

    is Drawing.WithClip ->
      clipEvaluator(
        objectEvaluator(drawing),
        intEvaluator(drawing.x),
        intEvaluator(drawing.y),
        intEvaluator(drawing.width),
        intEvaluator(drawing.height)
      )

    is Drawing.Label ->
      labelEvaluator(
        objectEvaluator(drawing.text),
        intEvaluator(drawing.x),
        intEvaluator(drawing.y)
      )

    is Drawing.Point ->
      pointEvaluator(
        intEvaluator(drawing.x),
        intEvaluator(drawing.y),
      )

    is Drawing.Line ->
      lineEvaluator(
        intEvaluator(drawing.x1),
        intEvaluator(drawing.y1),
        intEvaluator(drawing.x2),
        intEvaluator(drawing.y2),
      )

    is Drawing.Rect ->
      rectEvaluator(
        intEvaluator(drawing.x),
        intEvaluator(drawing.y),
        intEvaluator(drawing.width),
        intEvaluator(drawing.height),
      )

    is Drawing.Sprite ->
      spriteEvaluator(
        intEvaluator(drawing.x),
        intEvaluator(drawing.y),
        intEvaluator(drawing.width),
        intEvaluator(drawing.height),
        objectEvaluator(drawing.image),
        intEvaluator(drawing.imageX),
        intEvaluator(drawing.imageY),
      )

    is Drawing.Stack ->
      stackEvaluator(drawing.drawings.map { objectEvaluator<RuntimeDrawing>(it) }.toTypedArray())

    is Drawing.WithColor ->
      withColorEvaluator(
        objectEvaluator(drawing.drawing),
        objectEvaluator(drawing.color)
      )

    is Drawing.WithComposite ->
      withCompositeEvaluator(
        objectEvaluator(drawing.drawing),
        drawing.composite.tata8
      )

    is Drawing.WithFont ->
      withFontEvaluator(
        objectEvaluator(drawing.drawing),
        objectEvaluator(drawing.font)
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