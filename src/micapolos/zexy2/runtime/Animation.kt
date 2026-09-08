package micapolos.zexy2.runtime

interface Animation {
  fun start() {}
  fun step(seconds: Float): Float = 0f

  companion object
}

val Animation.Companion.empty get() = object : Animation {}

fun Animation.Companion.pause(seconds: Value<Double>): Animation = object : Animation {
  var remainingSeconds: Float = 0f

  override fun start() {
    remainingSeconds = seconds().toFloat()
  }

  override fun step(seconds: Float): Float {
    remainingSeconds -= seconds
    if (remainingSeconds >= 0) {
      return 0f
    } else {
      val leftoverSeconds = -remainingSeconds
      remainingSeconds = 0f
      return leftoverSeconds
    }
  }
}

fun Animation.show() {
  animated.show()
}

fun main() {
  Animation.pause(1.0.value).show()
}