package micapolos.zexy.model

sealed class Text : Value {
  data class Constant(val string: String): Text()
  data class Slice(val text: Value, val start: Value, val length: Value): Text()
  data class Join(val texts: List<Value>): Text()
}