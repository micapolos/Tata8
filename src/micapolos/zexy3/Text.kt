package micapolos.zexy3

sealed class Text : Value<Text> {
  class Constant(val string: String): Text()
}