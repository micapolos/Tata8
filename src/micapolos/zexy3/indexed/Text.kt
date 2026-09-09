package micapolos.zexy3.indexed

sealed class Text: Value<Text> {
  class Constant(val string: String): Text()
}