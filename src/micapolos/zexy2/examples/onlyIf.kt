package micapolos.zexy2.examples

import micapolos.zexy2.*
import micapolos.zexy2.live.show

fun main() {
  withVariable(0.0) { x ->
    parallel(
      x.keepAdding(-60.0).onlyIf(Key.LEFT.isPressed),
      x.keepAdding(60.0).onlyIf(Key.RIGHT.isPressed),
      drawCenteredChicken(x)
    )
  }.show()
}