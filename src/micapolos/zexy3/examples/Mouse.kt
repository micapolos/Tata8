package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(mouse.position)
    .show()
}