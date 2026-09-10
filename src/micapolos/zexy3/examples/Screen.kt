package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(
      position(
        screen.size.width - mouse.position.x,
        screen.size.height - mouse.position.y))
    .show()

}