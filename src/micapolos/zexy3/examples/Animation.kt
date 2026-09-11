package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val x = variable(100) {
    it.add(key.z.isPressed.selectTrueFalse(1, -1)).everyFrame
  }
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(position(x, 10))
    .show()

}