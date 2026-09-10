package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  game
    .withTitle("My first game")
    .with(sprite(image("/micapolos/quote.png"), 10, 10))
    .show()
}