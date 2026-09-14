package micapolos.zexy.model

sealed class Color : Value<Color> {
  data class Rgba(
    val red: Value<Number>,
    val green: Value<Number>,
    val blue: Value<Number>,
    val alpha: Value<Number>,
  ) : Color()
}