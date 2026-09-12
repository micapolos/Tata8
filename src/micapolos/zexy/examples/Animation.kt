package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val x = variable(100) {
    it.add(key.right.isPressed.selectTrueFalse(1, 0)).everyFrame
  }
  sprite
    .with(image("/micapolos/depressedChicken.png"))
    .with(position(x, 10))
    .show()

}