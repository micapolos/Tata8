package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  action {
    val x = variable(0)

    x set 10.value
    x capture x + 1

    sequence {
      x set 10.value
      x capture x + 1
    }

    frame.count.rem(2) select {
      x set 20.value
      x set 30.value
    }

    sprite
      .with(image("foo"))
      .draw()

    rect
      .with(position(10, 10))
      .with(size(30, 30))
      .draw()
  }
}