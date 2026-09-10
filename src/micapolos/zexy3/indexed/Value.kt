package micapolos.zexy3.indexed

sealed interface Value<T : Value<T>> {
  class Logged<T : Value<T>>(val label: String?, val value: Value<T>) : Value<T>

  class Select<T : Value<T>>(val index: Value<Integer>, val options: List<Value<T>>) : Value<T>

  class Sequence<T: Value<T>>(val values: List<Value<T>>): Value<T>

  class Stretch<T: Value<T>>(val value: Value<T>, val factor: Value<Number>): Value<T>

  class RunWhile<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  class StartWhen<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  class Stateful<T: Value<T>>(val state: Value<Void>, val value: Value<T>): Value<T>

  class Race<T: Value<T>>(val value: Value<T>, val others: List<Value<T>>): Value<T>
}