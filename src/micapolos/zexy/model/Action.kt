package micapolos.zexy.model

sealed class Action: Value {
  data object Empty : Action()
  data class Set(val variable: Variable, val value: Value): Action()
  data class Bind(val variable: Variable, val value: Value): Action()
  data class Sequence(val actions: List<Value>): Action()
  data class Log(val label: String?, val value: Value): Action()
}