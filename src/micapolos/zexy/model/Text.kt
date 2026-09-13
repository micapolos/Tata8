package micapolos.zexy.model

sealed class Text : Value<Text> {
  class Constant(val string: String): Text()
  class Slice(val text: Value<Text>, val start: Value<Integer>, val length: Value<Integer>): Text()
  class Join(val texts: List<Value<Text>>): Text()
}