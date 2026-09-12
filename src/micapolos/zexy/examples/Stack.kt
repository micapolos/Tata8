package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  stack(
    label
      .with("Hello, world!")
      .with(position(10, 10))
      .with(color.yellow),
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(10, 30)),
    rect
      .with(position(10, 80))
      .with(size(100, 20))
      .with(color.green)
  ).show()
}