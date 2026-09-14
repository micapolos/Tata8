package micapolos.zexy.model

sealed class Text : Value<Text> {
  data class Constant(val string: String): Text()
  data class Slice(val text: Value<Text>, val start: Value<Integer>, val length: Value<Integer>): Text()
  data class Join(val texts: List<Value<Text>>): Text()
}