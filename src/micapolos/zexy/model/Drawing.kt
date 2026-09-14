package micapolos.zexy.model

sealed class Drawing : Value<Drawing> {
  data object Empty : Drawing()

  data class Point(
    val x: Value<Integer>,
    val y: Value<Integer>,
  ) : Drawing()

  data class Line(
    val x1: Value<Integer>,
    val y1: Value<Integer>,
    val x2: Value<Integer>,
    val y2: Value<Integer>,
  ) : Drawing()

  data class Rect(
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>,
  ) : Drawing()

  data class Sprite(
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>,
    val image: Value<Image>,
    val imageX: Value<Integer>,
    val imageY: Value<Integer>,
  ) : Drawing()

  data class Label(
    val text: Value<Text>,
    val x: Value<Integer>,
    val y: Value<Integer>,
  ) : Drawing()

  data class WithColor(val drawing: Value<Drawing>, val color: Value<Color>) : Drawing()

  data class WithFont(val drawing: Value<Drawing>, val font: Value<Font>) : Drawing()

  data class WithComposite(val drawing: Value<Drawing>, val composite: Composite) : Drawing()

  data class Stack(val drawings: List<Value<Drawing>>) : Drawing()

  data class WithClip(
    val drawing: Value<Drawing>,
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>,
  ) : Drawing()
}