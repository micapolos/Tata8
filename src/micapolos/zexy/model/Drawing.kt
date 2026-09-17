package micapolos.zexy.model

sealed class Drawing : Value {
  data object Empty : Drawing()

  data class Point(
    val x: Value,
    val y: Value,
  ) : Drawing()

  data class Line(
    val x1: Value,
    val y1: Value,
    val x2: Value,
    val y2: Value,
  ) : Drawing()

  data class Rect(
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
  ) : Drawing()

  data class Sprite(
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
    val image: Value,
    val imageX: Value,
    val imageY: Value,
  ) : Drawing()

  data class Label(
    val text: Value,
    val x: Value,
    val y: Value,
  ) : Drawing()

  data class WithColor(val drawing: Value, val color: Value) : Drawing()

  data class WithFont(val drawing: Value, val font: Value) : Drawing()

  data class WithComposite(val drawing: Value, val composite: Composite) : Drawing()

  data class Stack(val drawings: List<Value>) : Drawing()

  data class WithClip(
    val drawing: Value,
    val x: Value,
    val y: Value,
    val width: Value,
    val height: Value,
  ) : Drawing()
}