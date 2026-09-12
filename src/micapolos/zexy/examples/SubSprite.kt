package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(position(100, 100))
    .with(size(32, 32))
    .withImage(position(64, 0))
    .show()
}