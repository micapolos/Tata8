package micapolos.zexy2.examples

import micapolos.zexy2.*
import micapolos.zexy2.live.show

fun main() {
  withVariable(0.0) { x ->
    parallel(
      pause(1.0),
      drawCenteredChicken(x)
    )
  }.show()
}