package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    val x1 = variable(0)
    val x2 = variable(0)
    val x3 = variable(0)

    val selected = variable(0)

    runSelected(selected) {
      everyStep { x1 add 1 }
      everyStep { x2 add 1 }
      everyStep { x3 add 1 }
    }

    on(key.z.press) {
      selected set (selected + 1) % 3
    }

    stack(
      label
        .with("Press Z to select"),
      sprite
        .with(image("/micapolos/quote.png"))
        .with(position(x1, 50)),
      sprite
        .with(image("/micapolos/quote.png"))
        .with(position(x2, 100)),
      sprite
        .with(image("/micapolos/quote.png"))
        .with(position(x3, 150))
    )
  }
}