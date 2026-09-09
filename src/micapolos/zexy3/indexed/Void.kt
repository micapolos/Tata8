package micapolos.zexy3.indexed

sealed class Void : Value<Void> {
  object Empty : Void()
  object Pause : Void()
  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()
}