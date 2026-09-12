package micapolos

import micapolos.zexy.*

fun main() {
  val x = variable(10)
  game
    .withTitle("My first game")
    .with(sprite.with(image("/micapolos/depressedChicken.png")))
    .show()
}