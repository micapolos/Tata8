package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .withPosition(100.value, 100.value)
    .finishAfter(pause(1.0))
    .show()
}