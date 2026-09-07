package micapolos.zexy2.examples

import micapolos.DepressedChicken
import micapolos.zexy2.*
import micapolos.zexy2.live.show

fun main() {
  val animation = withVariable(0.0) { x ->
    parallel(
      pause(1.0),
      x.add(30.0).on(Key.RIGHT.press),
      x.subtract(30.0).on(Key.LEFT.press),
      drawSprite
        .with(DepressedChicken.image)
        .with(position(x, Screen.center.position.y))
    )
  }

  animation.show()
}