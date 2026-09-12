package micapolos.zexy.indexed

sealed class Void : Value<Void> {
  object Empty : Void()
  class Pause(val seconds: Value<Number>) : Void()
  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()
  class Capture<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()
  class Parallel(val values: List<Value<Void>>): Void()
  class Race(val values: List<Value<Void>>): Void()
}