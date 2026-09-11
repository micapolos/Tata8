package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val image = image("/micapolos/depressedChicken.png")

  sprite
    .with(image)
    .with(
      position(
        (screen.size.width - image.width) / 2,
        (screen.size.height - image.height) / 2))
    .show()
}