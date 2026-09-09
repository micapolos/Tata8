package micapolos.zexy3.model

sealed class Drawing : Value<Drawing> {
  object Empty : Drawing()

  object Rect : Drawing()

  class Sprite(val image: Value<Image>) : Drawing()

  class Label(val text: Value<Text>) : Drawing()

  class WithColor(val drawing: Value<Drawing>, val color: Value<Color>) : Drawing()

  class WithFont(val drawing: Value<Drawing>, val font: Value<Font>) : Drawing()

  class Translate(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  class Scale(val drawing: Value<Drawing>, val x: Value<Number>, val y: Value<Number>) : Drawing()

  class Rotate(val drawing: Value<Drawing>, val radians: Value<Number>) : Drawing()

  class Blend(val drawing: Value<Drawing>, val composite: Value<Composite>) : Drawing()

  class Stack(val drawings: List<Value<Drawing>>) : Drawing()
}