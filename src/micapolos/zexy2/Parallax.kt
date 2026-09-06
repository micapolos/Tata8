package micapolos.zexy2

import micapolos.zexy2.live.Live

class Parallax(val ratio: Live<Double>)

fun parallax(ratio: Double) = parallax(ratio.live)
fun parallax(ratio: Live<Double>) = Parallax(ratio)
