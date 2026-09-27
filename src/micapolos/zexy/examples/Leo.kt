package micapolos.zexy.examples
import micapolos.zexy.model.*
import micapolos.zexy.show
fun main() {
  val game = Game(
    "Leo Game",
    480,
    256,
    Drawing.Rect(
      Integer.Constant(10),
      Integer.Constant(20),
      Integer.Constant(30),
      Integer.Constant(40)),
    Animation.Once(Action.Empty))
  game.show()
}