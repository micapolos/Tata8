package micapolos.zexy.indexed

sealed class Drawing : Value {
  object Empty : Drawing()

  class Point(
    val x: Value,
    val y: Value,
  ) : Drawing()

  class Line(
    val x1: Value,
    val y1: Value,
    val x2: Value,
    val y2: Value,
  ) : Drawing()

  class Rect(
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
  ) : Drawing()

  class Sprite(
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
    val image: Value,
    val imageX: Value,
    val imageY: Value,
  ) : Drawing()

  class Label(
    val text: Value,
    val x: Value,
    val y: Value,
  ) : Drawing()

  class WithColor(val drawing: Value, val color: Value) : Drawing()

  class WithFont(val drawing: Value, val font: Value) : Drawing()

  class WithComposite(val drawing: Value, val composite: Composite) : Drawing()

  class Stack(val drawings: List<Value>) : Drawing()

  class WithClip(
    val drawing: Value,
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
  ) : Drawing()
}