package micapolos.zexy.indexed

sealed interface Value<T : Value<T>> {
  class Logged<T : Value<T>>(val label: String?, val value: Value<T>) : Value<T>

  class Select<T : Value<T>>(val index: Value<Integer>, val options: List<Value<T>>) : Value<T>
}