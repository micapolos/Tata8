package micapolos.zexy3.model

sealed class Void: Value<Void> {
  class Pause(var seconds: Value<Number>): Void()

  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()

  class Parallel(val values: List<Value<Void>>): Void()

  class Race(val values: List<Value<Void>>): Void()
}