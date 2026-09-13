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
      .with(color.green),
    point
      .with(position(10, 120))
      .with(color.yellow),
    line
      .withStart(position(10, 130))
      .withEnd(position(100, 150))
      .with(color.yellow)
  ).show()
}