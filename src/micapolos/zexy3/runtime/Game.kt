package micapolos.zexy3.runtime

import micapolos.tata8.Game as TataGame

class Game(
  val title: String,
  val animatedDrawing: Animated<Drawing>,
)

fun Game.show() {
  TataGame.title = title
  animatedDrawing.show()
}