package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  stack(
    rect
      .with(position(8, 8))
      .with(size(64, 96))
      .with(color.black),
    rect
      .with(position(72, 8))
      .with(size(64, 96))
      .with(color(0.5, 0.2, 0.3, 1.0)),
    rect
      .with(position(136, 8))
      .with(size(64, 96))
      .with(color(0.8, 0.9, 0.5, 1.0)),
    rect
      .with(position(200, 8))
      .with(size(64, 96))
      .with(color.white),
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(8, 8)),
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(8, 40))
      .with(Composite.MULTIPLY),
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(8, 72))
      .with(Composite.SOFT_LIGHT))
    .show()
}