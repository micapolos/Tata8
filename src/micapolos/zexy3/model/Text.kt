package micapolos.zexy3.model

sealed class Text : Value<Text> {
  class Constant(val string: String): Text()
}