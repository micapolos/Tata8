package micapolos.zexy2.examples

import micapolos.zexy2.*
import micapolos.zexy2.live.show

fun main() {
  withVariable(0.0) { x ->
    parallel(
      x.set(-100.0).on(Key.LEFT.press),
      x.set(100.0).on(Key.RIGHT.press),
      drawCenteredChicken(x)
    )
  }.show()
}