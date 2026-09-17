package micapolos.zexy.indexed

sealed class Color : Value {
  class Rgba(
    val red: Value,
    val green: Value,
    val blue: Value,
    val alpha: Value,
  ) : Color()
}