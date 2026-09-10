package micapolos

import micapolos.zexy3.*

fun main() {
  val x = newVariable(10)
  game
    .withResources(Sandbox::class)
    .withTitle("My first game")
    .with(
      rect(Mouse.x.logged.integer.loggedAs("mouse x") + 40, Mouse.y.integer.loggedAs("mouse y") + 40, 30.value, 30.value),
      sprite(image("quote.png"), Mouse.x.integer, Mouse.y.integer),
      sprite(image("quote.png"), 60, 60))
    .show()
}