package micapolos.zexy2

import micapolos.zexy2.live.Live

class Angle(val degrees: Live<Double>)

val angleZero = angle(0.0)
fun angle(degrees: Double) = angle(degrees.live)
fun angle(degrees: Live<Double>) = Angle(degrees)