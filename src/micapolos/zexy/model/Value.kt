package micapolos.zexy.model

sealed interface Value {
  data class Logged(val label: String?, val value: Value) : Value

  data class Select(val index: Value, val options: List<Value>) : Value
}