package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Angle(val degrees: Expression<Double>)
fun angle(degrees: Expression<Double>) = Angle(degrees)