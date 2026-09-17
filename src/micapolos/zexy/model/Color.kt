package micapolos.zexy.model

sealed class Color : Value {
  data class Rgba(
    val red: Value,
    val green: Value,
    val blue: Value,
    val alpha: Value,
  ) : Color()
}