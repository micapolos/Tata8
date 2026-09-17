package micapolos.zexy.model

sealed class Animation: Value {
  data object Infinite : Animation()
  data class Once(val action: Value) : Animation()
  data class EveryStep(val action: Value): Animation()
  data class Pause(val seconds: Value): Animation()
  data class Parallel(val animations: List<Value>): Animation()
  data class Race(val animations: List<Value>): Animation()
  data class Sequence(val animations: List<Value>): Animation()
  data class RepeatWhile(val animation: Value, val condition: Value): Animation()
  data class On(val trigger: Value, val animation: Value): Animation()
}