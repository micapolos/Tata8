package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(0)
  sprite
    .with(image("/micapolos/quote.png"))
    .with(position(x, 100))
    .withAnimation {
      startWhen(mouse.isPressed) {
        everyStep { x add 1 }
      }
    }
    .show()
}