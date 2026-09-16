package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(0)
  sprite
    .with(image("/micapolos/quote.png"))
    .with(position(x, 100))
    .withAnimation {
      on(mouse.press) {
        everyStep { x add 1 }
      }
    }
    .show()
}