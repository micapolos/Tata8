package micapolos.zexy3.runtime

interface Runner {
  fun start() {}
  fun step(seconds: Float): Float = seconds
}

val instantRunner = object : Runner {}

val foreverRunner = object : Runner {
  override fun step(seconds: Float): Float = 0f
}

fun startRunner(fn: () -> Unit): Runner =
  object: Runner {
    override fun start() {
      fn()
    }
  }