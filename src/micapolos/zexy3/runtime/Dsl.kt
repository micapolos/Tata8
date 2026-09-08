package micapolos.zexy3.runtime

import micapolos.zexy3.Animation
import micapolos.zexy3.Drawing
import micapolos.zexy3.Image
import micapolos.zexy3.Integer
import micapolos.zexy3.Number

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

fun number(d: Double): Number = Number.Constant(d)
fun newVariable(d: Double): Number = newVariable(number(d))
fun newVariable(initial: Number): Number = Number.Variable(initial)
operator fun Number.unaryMinus(): Number = Number.Negate(this)
operator fun Number.plus(d: Double): Number = plus(number(d))
operator fun Number.plus(number: Number): Number = Number.Plus(this, number)
operator fun Number.minus(d: Double): Number = minus(number(d))
operator fun Number.minus(number: Number): Number = Number.Minus(this, number)
operator fun Number.times(d: Double): Number = times(number(d))
operator fun Number.times(number: Number): Number = Number.Times(this, number)
val Number.fraction: Number get() = Number.Fraction(this)
val Integer.number: Number get() = Number.FromInteger(this)
fun Number.animated(animation: Animation) = Number.Animated(this as Number.Variable, animation)

fun image(fileName: String): Image = Image.Load(fileName)

fun sprite(image: Image, position: Position): Drawing = Drawing.Sprite(image, position.x, position.y)
fun stack(vararg drawings: Drawing): Drawing = Drawing.Stack(drawings.toList())