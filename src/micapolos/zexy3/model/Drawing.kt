package micapolos.zexy3.model

sealed class Drawing : Value<Drawing> {
  data object Empty : Drawing()

  data object Rect : Drawing()

  data class Sprite(val image: Value<Image>) : Drawing()

  data class Label(val text: Value<Text>) : Drawing()

  data class WithColor(val drawing: Value<Drawing>, val color: Value<Color>) : Drawing()

  data class WithFont(val drawing: Value<Drawing>, val font: Value<Font>) : Drawing()

  data class Translate(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  data class Scale(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  data class Rotate(val drawing: Value<Drawing>, val radians: Value<Number>) : Drawing()

  data class Blend(val drawing: Value<Drawing>, val composite: Value<Composite>) : Drawing()

  data class Stack(val drawings: List<Value<Drawing>>) : Drawing()
}