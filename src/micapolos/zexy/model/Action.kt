package micapolos.zexy.model

sealed class Action {
  data object Empty : Action()
  data class Set<T: Value<T>>(val variable: Variable<T>, val value: Value<T>): Action()
  data class Bind<T: Value<T>>(val variable: Variable<T>, val value: Value<T>): Action()
  data class Sequence(val actions: List<Action>): Action()
  data class Select(val index: Value<Integer>, val actions: List<Action>): Action()
}