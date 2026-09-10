package micapolos.zexy3.model

sealed class Struct: Value<Struct> {
  class Make(val name: String, val values: List<Value<*>>): Struct()
}