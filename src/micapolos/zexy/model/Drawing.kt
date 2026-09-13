package micapolos.zexy.model

sealed class Drawing : Value<Drawing> {
  object Empty : Drawing()

  class Point(
    val x: Value<Integer>,
    val y: Value<Integer>,
  ) : Drawing()

  class Line(
    val x1: Value<Integer>,
    val y1: Value<Integer>,
    val x2: Value<Integer>,
    val y2: Value<Integer>,
  ) : Drawing()

  class Rect(
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>,
  ) : Drawing()

  class Sprite(
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>,
    val image: Value<Image>,
    val imageX: Value<Integer>,
    val imageY: Value<Integer>,
  ) : Drawing()

  class Label(
    val text: Value<Text>,
    val x: Value<Integer>,
    val y: Value<Integer>,
  ) : Drawing()

  class WithColor(val drawing: Value<Drawing>, val color: Value<Color>) : Drawing()

  class WithFont(val drawing: Value<Drawing>, val font: Value<Font>) : Drawing()

  class WithComposite(val drawing: Value<Drawing>, val composite: Composite) : Drawing()

  class Stack(val drawings: List<Value<Drawing>>) : Drawing()
}