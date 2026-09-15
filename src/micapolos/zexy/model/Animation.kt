package micapolos.zexy.model

sealed class Animation {
  data object Infinite : Animation()
  data class Once(val action: Action) : Animation()
  data class EveryStep(val action: Action): Animation()
  data class Pause(val seconds: Value<Number>): Animation()
  data class Parallel(val animations: List<Animation>): Animation()
  data class Race(val animations: List<Animation>): Animation()
  data class Sequence(val animations: List<Animation>): Animation()
  data class RepeatWhile(val animation: Animation, val condition: Value<Integer>): Animation()
  data class SelectStart(val index: Value<Integer>, val animations: List<Animation>): Animation()
  data class SelectStep(val index: Value<Integer>, val animations: List<Animation>): Animation()
}