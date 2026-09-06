package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.livePause

val Double.pause get() = live.pause
val Live<Double>.pause get() = livePause(this)