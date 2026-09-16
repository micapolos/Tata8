package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    val x = variable(0)

    on(mouse.press) {
      everyStep { x add 1 }
    }

    sprite
      .with(image("/micapolos/quote.png"))
      .with(position(x, 100))
  }
}