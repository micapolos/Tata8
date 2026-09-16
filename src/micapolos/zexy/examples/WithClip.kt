package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    stack(
      sprite
        .with(image("/micapolos/depressedChicken.png"))
        .with(position(0, 0))
        .with(clip(position(10, 10), size(128, 5))),
      sprite
        .with(image("/micapolos/depressedChicken.png"))
        .with(position(0, 32))
    ).with(clip(position(20, 0), size(64, 64)))
  }
}