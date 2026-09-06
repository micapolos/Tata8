package micapolos.zexy2

import micapolos.tata8.Composite
import micapolos.tata8.Image
import micapolos.tata8.Sprite

import micapolos.zexy2.ast.Expression

fun sprite() =
  Expression.Application<Animation<Sprite>>(
    Animation::class,
    "sprite",
    listOf(
      constant(Image::class, null),
      constant(0.0), constant(0.0),
      constant(0.0), constant(0.0),
      constant(false), constant(false),
      constant(1.0), constant(1.0),
      constant(Composite::class, Composite.NORMAL),
      constant(0.0)))

fun Expression.Application<Animation<Sprite>>.with(image: Image) =
  with(constant(Image::class, image))

fun Expression.Application<Animation<Sprite>>.with(image: Expression<Image>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(image, args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withAnchor(x: Double, y: Double) =
  withAnchor(constant(x), constant(y))

fun Expression.Application<Animation<Sprite>>.withAnchor(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], x, y, args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withPosition(x: Double, y: Double) =
  withPosition(constant(x), constant(y))

fun Expression.Application<Animation<Sprite>>.withPosition(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], x, y, args[5], args[6], args[7], args[8], args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withFlip(x: Boolean, y: Boolean) =
  withFlip(constant(x), constant(y))

fun Expression.Application<Animation<Sprite>>.withFlip(x: Expression<Boolean>, y: Expression<Boolean>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], x, y, args[7], args[8], args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withScale(x: Double, y: Double) =
  withScale(constant(x), constant(y))

fun Expression.Application<Animation<Sprite>>.withScale(x: Expression<Double>, y: Expression<Double>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], x, y, args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withComposite(composite: Composite) =
  withComposite(constant(Composite::class, composite))

fun Expression.Application<Animation<Sprite>>.withComposite(composite: Expression<Composite>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], composite, args[9], args[10]))

fun Expression.Application<Animation<Sprite>>.withParallax(parallax: Double) =
  withParallax(constant(parallax))

fun Expression.Application<Animation<Sprite>>.withParallax(parallax: Expression<Double>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], parallax, args[10]))

fun Expression.Application<Animation<Sprite>>.withAngle(angle: Double) =
  withAngle(constant(angle))

fun Expression.Application<Animation<Sprite>>.withAngle(angle: Expression<Double>) =
  Expression.Application<Animation<Sprite>>(kClass, name, listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], angle))

