package micapolos.zexy3.model

sealed class Void: Value<Void> {
  data object Pause: Void()

  data class Set<T : Value<T>>(val variable: Variable<T>, val value: Value<T>) : Void()
}