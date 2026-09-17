package micapolos.zexy.indexed

sealed class Action: Value {
  object Empty : Action()
  class Set(val variable: Variable, val value: Value) : Action()
  class Bind(val variable: Variable, val value: Value) : Action()
  class Sequence(val actions: List<Value>) : Action()
  class Log(val label: String?, val value: Value): Action()
}