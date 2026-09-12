package micapolos.zexy.model

sealed class Text : Value<Text> {
  class Constant(val string: String): Text()
}