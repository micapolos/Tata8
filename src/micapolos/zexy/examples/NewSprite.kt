package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    once {
      sprite
        .with(image("/micapolos/depressedChicken.png"))
        .with(position(100, 100))
    }
  }.show()
}