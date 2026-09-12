package micapolos.zexy.indexed

sealed class Text: Value<Text> {
  class Constant(val string: String): Text()
}