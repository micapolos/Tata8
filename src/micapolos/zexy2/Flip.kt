package micapolos.zexy2

import micapolos.zexy2.live.Live

class Flip(val x: Live<Boolean>, val y: Live<Boolean>)

val noFlip = flip(false, false)
fun flip(x: Boolean, y: Boolean) = Flip(x.live, y.live)
fun flip(x: Live<Boolean>, y: Boolean) = Flip(x, y.live)
fun flip(x: Boolean, y: Live<Boolean>) = Flip(x.live, y)
fun flip(x: Live<Boolean>, y: Live<Boolean>) = Flip(x, y)