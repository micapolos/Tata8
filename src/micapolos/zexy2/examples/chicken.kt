package micapolos.zexy2.examples

import micapolos.DepressedChicken
import micapolos.zexy2.Screen
import micapolos.zexy2.center
import micapolos.zexy2.centerBottomAlignment
import micapolos.zexy2.drawSprite
import micapolos.zexy2.live.Live
import micapolos.zexy2.plus
import micapolos.zexy2.position
import micapolos.zexy2.with

fun drawCenteredChicken(x: Live<Double>) =
  drawSprite
    .with(DepressedChicken.images[0])
    .with(centerBottomAlignment)
    .with(position(Screen.center.position.x + x, Screen.center.position.y))