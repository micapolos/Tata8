package micapolos.zexy3

sealed class Drawing : Value<Drawing> {
  data object Empty : Drawing()

  data object Rect : Drawing()

  data class Sprite(val image: Value<Image>) : Drawing()

  data class WithColor(val drawing: Value<Drawing>, val color: Color) : Drawing()

  data class WithFont(val drawing: Value<Drawing>, val font: Value<Font>) : Drawing()

  data class Translate(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  data class Scale(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  data class Rotate(val drawing: Value<Drawing>, val radians: Value<Number>) : Drawing()

  data class Blend(val drawing: Value<Drawing>, val composite: Composite) : Drawing()

  data class Stack(val drawings: List<Value<Drawing>>) : Drawing()
}