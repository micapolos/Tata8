package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Angle(val degrees: Live<Double>)
fun angle(degrees: Live<Double>) = Angle(degrees)