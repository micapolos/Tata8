package micapolos.zexy3.indexed

sealed class Struct: Value<Struct> {
  class Make(val name: String, val values: List<Value<*>>): Struct()
}