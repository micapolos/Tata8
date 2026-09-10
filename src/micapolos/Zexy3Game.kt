package micapolos

import micapolos.zexy3.*

fun main() {
  val x = variable(10)
  game
    .withResources(Sandbox::class)
    .withTitle("My first game")
    .with(sprite.with(image("/micapolos/socksgirl-sheet.png")))
    .show()
}