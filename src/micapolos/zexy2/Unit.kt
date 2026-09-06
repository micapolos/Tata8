package micapolos.zexy2

import micapolos.zexy2.live.Run

object DoNothing : Run

val doNothing get() = DoNothing.live(DoNothing::class)