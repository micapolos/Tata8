package micapolos.zexy.indexed

sealed class Animation {
  object Empty : Animation()
  class Once(val action: Action) : Animation()
  class EveryFrame(val action: Action): Animation()
  class Pause(val seconds: Value<Number>): Animation()
  class Parallel(val animations: List<Animation>): Animation()
  class Race(val animations: List<Animation>): Animation()
  class Sequence(val animations: List<Animation>): Animation()
  class RepeatWhile(val animation: Animation, val condition: Value<Integer>): Animation()
  class SelectStart(val index: Value<Integer>, val animations: List<Animation>): Animation()
  class SelectStep(val index: Value<Integer>, val animations: List<Animation>): Animation()
}