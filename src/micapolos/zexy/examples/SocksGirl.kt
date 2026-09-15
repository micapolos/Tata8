package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val imageIndex = variable(0)
  val girlSize = size(64, 64)
  sprite
    .with(image("/micapolos/socksgirl-sheet.png"))
    .with(position((screen.size.width - 64) / 2, 160))
    .with(girlSize)
    .withImage(position(imageIndex * girlSize.width, 0))
    .withAnimation {
      repeat {
        imageIndex set (imageIndex + 1) % 46
        this pause 0.15
      }
    }
    .show()
}