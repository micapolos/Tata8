package micapolos

import micapolos.zexy3.*

fun main() {
  val x = newVariable(10)
  val game = game(
    resourcesClass = Sandbox::class,
    title = "My first game",
    drawing = stack(
      rect(Mouse.x.logged.integer.loggedAs("mouse x") + 40, Mouse.y.integer.loggedAs("mouse y") + 40, 30.value, 30.value),
      sprite(image("quote.png"), Mouse.x.integer, Mouse.y.integer),
      sprite(image("quote.png"), 60, 60)))
  game.show()
}