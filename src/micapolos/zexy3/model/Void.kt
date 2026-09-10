package micapolos.zexy3.model

sealed class Void: Value<Void> {
  object Empty: Void()

  class Pause(var seconds: Value<Number>): Void()

  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()

  class Parallel<T: Value<T>>(val values: List<Value<*>>): Void()
}