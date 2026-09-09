package micapolos.zexy3.runtime

import micapolos.tata8.Game

class Animated<T>(val evaluator: Evaluator<T>, val animation: Animation)

fun Animated<Drawing>.show() {
  animation.start()
  Game.onUpdate = { animation.step(1/60.0) }
  Game.start()
}