package micapolos.zexy.model

sealed interface Value<T : Value<T>> {
  data class Logged<T : Value<T>>(val label: String?, val value: Value<T>) : Value<T>

  data class Select<T : Value<T>>(val index: Value<Integer>, val options: List<Value<T>>) : Value<T>

  data class Sequence<T: Value<T>>(val values: List<Value<T>>): Value<T>

  data class Stretch<T: Value<T>>(val value: Value<T>, val factor: Value<Number>): Value<T>

  data class RunWhile<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  data class RepeatWhile<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  data class StartWhen<T: Value<T>>(val condition: Value<Integer>, val value: Value<T>): Value<T>

  data class Stateful<T: Value<T>>(val state: Value<*>, val value: Value<T>): Value<T>

  data class Race<T: Value<T>>(val value: Value<T>, val others: List<Value<T>>): Value<T>

  data class EveryFrame<T: Value<T>>(val value: Value<T>): Value<T>

  data class NextFrame<T: Value<T>>(val value: Value<T>): Value<T>

  data class Pulse<T: Value<T>>(val high: Value<T>, val low: Value<T>): Value<T>
}