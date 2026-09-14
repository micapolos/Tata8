package micapolos.zexy.model

sealed class Void: Value<Void> {
  data object Empty: Void()

  data class Pause(val seconds: Value<Number>): Void()

  data class Bind<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()

  data class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()

  data class Parallel(val values: List<Value<Void>>): Void()

  data class Race(val values: List<Value<Void>>): Void()
}