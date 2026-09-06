package micapolos.zexy2

import micapolos.zexy2.live.Live

class Parallax(val ratio: Live<Double>)

val noParallax = parallax(0.0)
fun parallax(ratio: Double) = parallax(ratio.live)
fun parallax(ratio: Live<Double>) = Parallax(ratio)
