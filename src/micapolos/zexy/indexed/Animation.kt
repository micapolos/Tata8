package micapolos.zexy.indexed

sealed class Animation: Value {
  object Empty : Animation()
  class Once(val action: Value) : Animation()
  class EveryStep(val action: Value): Animation()
  class Pause(val seconds: Value): Animation()
  class Parallel(val animations: List<Value>): Animation()
  class Race(val animations: List<Value>): Animation()
  class Sequence(val animations: List<Value>): Animation()
  class RepeatWhile(val animation: Value, val condition: Value): Animation()
  class On(val trigger: Value, val animation: Value): Animation()
}