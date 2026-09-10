package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  stack(
    label
      .with("Hello, this is Kora font.")
      .withPosition(8, 8)
      .with(font("/micapolos/tata8/kora-font.png")),
    label
      .with("Hello, this is Mica font.")
      .withPosition(8, 24)
      .with(font("/micapolos/tata8/mica-font.png"))
  ).show()
}