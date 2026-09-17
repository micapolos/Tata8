package micapolos.zexy.indexed

sealed class Text: Value {
  class Constant(val string: String): Text()
  class Slice(val text: Value, val start: Value, val length: Value): Text()
  class Join(val texts: List<Value>): Text()
}