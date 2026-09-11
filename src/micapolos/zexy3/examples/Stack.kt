package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  stack(
    label
      .with("Hello, world!")
      .with(position(10, 10)),
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(10, 30))
  ).show()
}