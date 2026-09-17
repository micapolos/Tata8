package micapolos.zexy.indexed

sealed interface Value {
  class Logged(val label: String?, val value: Value) : Value

  class Select(val index: Value, val options: List<Value>) : Value
}