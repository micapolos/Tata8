package micapolos.zexy3

sealed class Number {
  class Constant(val d: Double): Number()
  class Variable(val initial: Number): Number()

  class Negate(val a: Number): Number()
  class Plus(val a: Number, val b: Number): Number()
  class Minus(val a: Number, val b: Number): Number()
  class Times(val a: Number, val b: Number): Number()
  class Fraction(val a: Number): Number()
  class FromInteger(val i: Integer): Number()

  class Conditional(val condition: Bool, val trueNumber: Number, val falseNumber: Number): Number()

  class Animated(val variable: Variable, val animation: Animation): Number()
  class Logged(val number: Number, val label: String?): Number()

  object FrameSeconds: Number()
}

fun number(d: Double): Number = Number.Constant(d)

fun newVariable(initial: Number): Number = Number.Variable(initial)

fun Number.plus(d: Double): Number = plus(number(d))
fun Number.plus(number: Number): Number = Number.Plus(this, number)

val frameSeconds: Number = Number.FrameSeconds