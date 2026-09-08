package micapolos.zexy3

sealed class Animation : Value<Animation> {
  object Instant: Animation()
  object Forever: Animation()
  class Pause(val seconds: Number): Animation()
  class WithAction(val action: Action): Animation()
  class Parallel(val animations: List<Animation>): Animation()
  class Sequence(val animations: List<Animation>): Animation()
  class StartOn(val animation: Animation, val bool: Bool): Animation()
  class StopOn(val animation: Animation, val bool: Bool): Animation()
  class RunWhile(val animation: Animation, val bool: Bool): Animation()
  class Select(val animations: List<Animation>, val integer: Integer): Animation()
  class Stretch(val animation: Animation, val factor: Number): Animation()
}

val Action.animation: Animation get() = Animation.WithAction(this)

fun pause(seconds: Double): Animation = pause(number(seconds))
fun pause(seconds: Number): Animation = Animation.Pause(seconds)
