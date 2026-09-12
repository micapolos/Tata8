package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  stack(8 * 8) { index ->
    sprite
      .with(image("/micapolos/quote.png"))
      .with(position(index % 8 * 32, index / 8 * 32))
  }.show()
}