package micapolos.zexy2.examples

import micapolos.DepressedChicken
import micapolos.zexy2.*
import micapolos.zexy2.live.show

fun main() {
  val animation = withVariable(0.0) { x ->
    parallel(
      x.add(100.0).on(Key.RIGHT.press),
      x.subtract(100.0).on(Key.LEFT.press),
      drawSprite
        .with(DepressedChicken.image)
        .with(position(x.elastic, Screen.center.position.y))
    )
  }

  animation.show()
}