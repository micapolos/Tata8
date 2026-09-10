package micapolos.zexy3.indexed

sealed class Void : Value<Void> {
  class Pause(val seconds: Value<Number>) : Void()
  class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()
  class Parallel<T: Value<T>>(val values: List<Value<*>>): Void()
}