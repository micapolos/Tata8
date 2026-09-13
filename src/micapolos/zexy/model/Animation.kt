package micapolos.zexy.model

sealed class Animation {
  object Instant : Animation()
  object Infinite : Animation()
  class Pause(val seconds: Value<Number>): Animation()
  class Capture<T: Value<T>>(val variable: Variable<T>, val value: Value<T>): Animation()
  class Set<T: Value<T>>(val variable: Variable<T>, val value: Value<T>): Animation()
  class Parallel(val animations: List<Animation>): Animation()
  class Race(val animations: List<Animation>): Animation()
  class Sequence(val animations: List<Animation>): Animation()
  class RepeatWhile(val animation: Animation, val condition: Value<Integer>): Animation()
  class SelectStart(val index: Value<Integer>, val animations: List<Animation>): Animation()
  class SelectStep(val index: Value<Integer>, val animations: List<Animation>): Animation()
}