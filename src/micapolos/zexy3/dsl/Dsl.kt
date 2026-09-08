package micapolos.zexy3.dsl

import micapolos.zexy3.*
import micapolos.zexy3.Number

// TODO: Move to corresponding model files.

object Screen {
  val width: Integer = Integer.ScreenWidth
  val height: Integer = Integer.ScreenHeight
}

object Frame {
  val seconds: Number = Number.FrameSeconds
}

class Position(val x: Number, val y: Number)
fun position(x: Double, y: Double) = position(number(x), number(y))
fun position(x: Double, y: Number) = position(number(x), y)
fun position(x: Number, y: Double) = position(x, number(y))
fun position(x: Number, y: Number) = Position(x, y)

fun bool(b: Boolean): Bool = Bool.Constant(b)

fun integer(i: Int): Integer = Integer.Constant(i)

fun number(d: Double): Number = Number.Constant(d)
operator fun Number.unaryMinus(): Number = Number.Negate(this)
//operator fun Number.plus(d: Double): Number = plus(number(d))
//operator fun Number.plus(number: Number): Number = Number.Plus(this, number)
operator fun Number.minus(d: Double): Number = minus(number(d))
operator fun Number.minus(number: Number): Number = Number.Minus(this, number)
operator fun Number.times(d: Double): Number = times(number(d))
operator fun Number.times(number: Number): Number = Number.Times(this, number)
val Number.fraction: Number get() = Number.Fraction(this)
val Integer.number: Number get() = Number.FromInteger(this)
fun Number.animated(animation: Animation) = Number.Animated(this as Number.Variable, animation)
val Number.logged: Number get() = Number.Logged(this, null)
fun Number.loggedAs(label: String): Number = Number.Logged(this, label)

fun image(fileName: String): Image = Image.Load(fileName)

fun sprite(image: Image, position: Position): Drawing = Drawing.Sprite(image, position.x, position.y)
fun stack(vararg drawings: Drawing): Drawing = Drawing.Stack(drawings.toList())

fun Bool.set(bool: Bool): Action = Action.BoolSet(this, bool)
fun Integer.set(integer: Integer): Action = Action.IntegerSet(this, integer)
fun Number.set(number: Number): Action = Action.NumberSet(this, number)
fun Number.capture(number: Number): Action = Action.NumberCapture(this, number)

class IfTrueNumber(val condition: Bool, val trueNumber: Number)
fun Bool.ifTrue(d: Double) = ifTrue(number(d))
fun Bool.ifTrue(number: Number) = IfTrueNumber(this, number)
fun IfTrueNumber.orElse(falseNumber: Double): Number = orElse(number(falseNumber))
fun IfTrueNumber.orElse(falseNumber: Number): Number = Number.Conditional(condition, trueNumber, falseNumber)

val Key.isPressed: Bool get() = Bool.KeyPressed(this)