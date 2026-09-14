package micapolos.zexy.indexed

sealed class Action {
  object Empty : Action()
  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Action()
  class Bind<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Action()
  class Sequence(val actions: List<Action>) : Action()
  class Select(val index: Value<Integer>, val actions: List<Action>) : Action()
}