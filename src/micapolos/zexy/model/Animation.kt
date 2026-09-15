package micapolos.zexy.model

sealed class Animation: Value<Animation> {
  data object Infinite : Animation()
  data class Once(val action: Action) : Animation()
  data class EveryStep(val action: Action): Animation()
  data class Pause(val seconds: Value<Number>): Animation()
  data class Parallel(val animations: List<Animation>): Animation()
  data class Race(val animations: List<Animation>): Animation()
  data class Sequence(val animations: List<Animation>): Animation()
  data class RepeatWhile(val animation: Animation, val condition: Value<Integer>): Animation()
  data class StartOn(val trigger: Value<Integer>, val animation: Value<Animation>): Animation()
}