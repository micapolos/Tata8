package micapolos.ast

import micapolos.tata8.Composite
import micapolos.tata8.Game
import micapolos.tata8.Image
import micapolos.tata8.Sprite
import kotlin.reflect.KClass

internal var nextId = 0

val <T> Expression<T>.variable: Expression.Variable<T> get() =
  this as? Expression.Variable<T> ?: error("Not a variable")

val <T> Expression<T>.logged get() =
  Expression.Application<T>(kClass, "logged", listOf(this))

fun <T> Expression<T>.loggedAs(name: String) =
  Expression.Application<T>(kClass, "logged", listOf(constant(String::class, name), this))

val <T> Expression<T>.readOnly get() =
  Expression.Application<T>(kClass, "readOnly", listOf(this))

fun constant(b: Boolean): Expression<Boolean> =
  Expression.Constant(Boolean::class, b)

fun constant(i: Int): Expression<Int> =
  Expression.Constant(Int::class, i)

fun constant(d: Double): Expression<Double> =
  Expression.Constant(Double::class, d)

fun <T> constant(kClass: KClass<*>, value: T): Expression<T> =
  Expression.Constant(kClass, value)

fun variable(b: Boolean): Expression<Boolean> = variable(constant(b))

fun variable(i: Int): Expression<Int> = variable(constant(i))

fun variable(d: Double): Expression<Double> = variable(constant(d))

fun <T> variable(initializer: Expression<T>): Expression<T> {
  nextId++
  return Expression.Variable(Int::class, nextId, initializer)
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Unit> =
  Expression.Set(variable, expression)

operator fun Expression<Int>.plus(i: Int): Expression<Int> = plus(constant(i))

@JvmName("plusInt")
operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.plus", listOf(this, expression))

operator fun Expression<Double>.plus(i: Double): Expression<Double> = plus(constant(i))

@JvmName("plusDouble")
operator fun Expression<Double>.plus(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.plus", listOf(this, expression))

operator fun Expression<Int>.minus(i: Int): Expression<Int> = minus(constant(i))

@JvmName("minusInt")
operator fun Expression<Int>.minus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.minus", listOf(this, expression))

operator fun Expression<Double>.minus(d: Double): Expression<Double> = minus(constant(d))

@JvmName("minusDouble")
operator fun Expression<Double>.minus(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.minus", listOf(this, expression))

operator fun Expression<Int>.times(i: Int): Expression<Int> = times(constant(i))

@JvmName("timesInt")
operator fun Expression<Int>.times(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.times", listOf(this, expression))

operator fun Expression<Double>.times(d: Double): Expression<Double> = minus(constant(d))

@JvmName("timesDouble")
operator fun Expression<Double>.times(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.times", listOf(this, expression))

fun animation(vararg expressions: Expression<*>): Expression<Unit> =
  Expression.Application(Unit::class, "sequence", expressions.asList())

fun Expression<Int>.keepAdding(i: Int): Expression<Unit> = keepAdding(constant(i))

@JvmName("keepAddingInt")
fun Expression<Int>.keepAdding(expression: Expression<Int>): Expression<Unit> =
  Expression.Application(Unit::class, "Int.keepAdding", listOf(variable, expression))

fun Expression<Double>.keepAdding(d: Double): Expression<Unit> = keepAdding(constant(d))

@JvmName("keepAddingDouble")
fun Expression<Double>.keepAdding(expression: Expression<Double>): Expression<Unit> =
  Expression.Application(Unit::class, "Double.keepAdding", listOf(variable, expression))

val screenWidth = constant(Game.WIDTH.toDouble())
val screenHeight = constant(Game.HEIGHT.toDouble())

fun image(baseClass: KClass<*>, name: String): Expression<Image> =
  Expression.Application(
    Image::class, "loadImage",
    listOf(
      constant(KClass::class, baseClass),
      constant(String::class, name)))

fun sprite() =
  Expression.Application<Sprite>(
    Unit::class, "sprite",
    listOf(
      constant(Image::class, null),
      constant(0.0), constant(0.0),
      constant(0.0), constant(0.0),
      constant(false), constant(false),
      constant(1.0), constant(1.0),
      constant(Composite::class, Composite.NORMAL),
      constant(0.0)))

fun Expression.Application<Sprite>.with(image: Image) =
  with(constant(Image::class, image))

fun Expression.Application<Sprite>.with(image: Expression<Image>) =
  Expression.Application<Sprite>(kClass, name, listOf(image, args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Sprite>.withAnchor(x: Double, y: Double) =
  withAnchor(constant(x), constant(y))

fun Expression.Application<Sprite>.withAnchor(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], x, y, args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Sprite>.withPosition(x: Double, y: Double) =
  withPosition(constant(x), constant(y))

fun Expression.Application<Sprite>.withPosition(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], x, y, args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Sprite>.withFlip(x: Boolean, y: Boolean) =
  withFlip(constant(x), constant(y))

fun Expression.Application<Sprite>.withFlip(x: Expression<Boolean>, y: Expression<Boolean>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], x, y, args[7], args[8], args[9], args[10]))

fun Expression.Application<Sprite>.withScale(x: Double, y: Double) =
  withScale(constant(x), constant(y))

fun Expression.Application<Sprite>.withScale(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], x, y, args[9], args[10]))

fun Expression.Application<Sprite>.withComposite(composite: Composite) =
  withComposite(constant(Composite::class, composite))

fun Expression.Application<Sprite>.withComposite(composite: Expression<Composite>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], composite, args[9], args[10]))

fun Expression.Application<Sprite>.withParallax(parallax: Double) =
  withParallax(constant(parallax))

fun Expression.Application<Sprite>.withParallax(parallax: Expression<Double>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], parallax, args[10]))

fun Expression.Application<Sprite>.withAngle(angle: Double) =
  withAngle(constant(angle))

fun Expression.Application<Sprite>.withAngle(angle: Expression<Double>) =
  Expression.Application<Sprite>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], angle))
