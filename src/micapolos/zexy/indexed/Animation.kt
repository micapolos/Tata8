package micapolos.zexy.indexed

sealed class Animation: Value<Animation> {
  object Empty : Animation()
  class Once(val action: Value<Action>) : Animation()
  class EveryStep(val action: Value<Action>): Animation()
  class Pause(val seconds: Value<Number>): Animation()
  class Parallel(val animations: List<Value<Animation>>): Animation()
  class Race(val animations: List<Value<Animation>>): Animation()
  class Sequence(val animations: List<Value<Animation>>): Animation()
  class RepeatWhile(val animation: Value<Animation>, val condition: Value<Integer>): Animation()
  class On(val trigger: Value<Integer>, val animation: Value<Animation>): Animation()
}