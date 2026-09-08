package micapolos.zexy3

sealed class Drawing : Value {
  class Variable(val initial: Drawing): Drawing()
  class Sprite(
    val image: Image,
    val x: Number,
    val y: Number,
  ): Drawing()
  class Label(
    val text: Text,
    val x: Number,
    val y: Number,
    val color: Color,
    val font: Font,
    val shadow: Bool,
  ): Drawing()
  class Stack(val drawings: List<Drawing>): Drawing()
  class Translate(val drawing: Drawing, val x: Number, val y: Number): Drawing()
  class Scale(val drawing: Drawing, val x: Number, val y: Number): Drawing()
  class Rotate(val drawing: Drawing, val angle: Number): Drawing()
  class WithFont(val drawing: Drawing, val font: Font): Drawing()
  class WithColor(val drawing: Drawing, val color: Color): Drawing()
  class WithComposite(val drawing: Drawing, val composite: Composite): Drawing()
  class WithParallax(val drawing: Drawing, val parallax: Number): Drawing()
  class Select(val drawing: List<Drawing>, val integer: Integer): Drawing()
}