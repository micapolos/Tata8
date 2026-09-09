package micapolos.zexy3.model

sealed class Color : Value<Color> {
  class Rgba(
    val red: Value<Number>,
    val green: Value<Number>,
    val blue: Value<Number>,
    val alpha: Value<Number>,
  ) : Color()
}