package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  show {
    val x = variable(0)
    val y = variable(0)

    on(mouse.press) {
      x set mouse.position.x
      y set mouse.position.y
    }

    val ratio = 15 by 16

    sprite
      .with(image("/micapolos/quote.png"))
      .with(position(x.elastic(ratio), y.elastic(ratio)))
  }
}

