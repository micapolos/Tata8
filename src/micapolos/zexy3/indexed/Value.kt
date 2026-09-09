package micapolos.zexy3.indexed

sealed interface Value<T : Value<T>> {
  class Logged<T : Value<T>>(val label: String?, val value: Value<T>) : Value<T>

  class Capture<T : Value<T>>(val value: Value<T>) : Value<T>

  class Select<T : Value<T>>(
    val index: Value<Integer>,
    val options: List<Value<T>>,
    val defaultValue: Value<T>
  ) : Value<T>

  class Sequence<T: Value<T>>(val preceding: List<Value<*>>, val result: Value<T>): Value<T>

  class Parallel<T: Value<T>>(val background: List<Value<*>>, val result: Value<T>): Value<T>

  class Stretch<T: Value<T>>(val value: Value<T>, val factor: Value<Number>): Value<T>

  class RunWhile<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  class StartWhen<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>
}