package micapolos.zexy3

sealed class Animation {
  object Instant: Animation()
  class Pause(val seconds: Number): Animation()
  class WithAction(val action: Action): Animation()
  class Parallel(val animations: List<Animation>): Animation()
  class Sequence(val animations: List<Animation>): Animation()
  class StartOn(val animation: Animation, val bool: Bool): Animation()
  class StopOn(val animation: Animation, val bool: Bool): Animation()
  class RunWhile(val animation: Animation, val bool: Bool): Animation()
  class Select(val animations: List<Animation>, val integer: Integer): Animation()
  class Stretch(val animation: Animation, val factor: Number): Animation()
  class GenSequence(val count: Int, fn: (Int) -> Animation): Animation()
  class GenParallel(val count: Int, fn: (Int) -> Animation): Animation()
}