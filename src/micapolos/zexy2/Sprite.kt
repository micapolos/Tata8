package micapolos.zexy2

import micapolos.tata8.Composite
import micapolos.tata8.Image
import micapolos.tata8.Sprite

import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import micapolos.zexy2.ast.asApplication

typealias LiveSprite = Live<Animation<Sprite>>

val sprite: LiveSprite
  get() =
    Live.Application(
      Animation::class,
      Primitive.SPRITE,
      listOf(
        constant(Image::class, null),
        constant(0.0), constant(0.0),
        constant(0.0), constant(0.0),
        constant(false), constant(false),
        constant(1.0), constant(1.0),
        constant(Composite::class, Composite.NORMAL),
        constant(0.0)
      )
    )

fun Live<Animation<Sprite>>.with(image: Image): LiveSprite =
  with(constant(Image::class, image))

fun Live<Animation<Sprite>>.with(image: Live<Image>): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(image, args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10])
  )
}

fun Live<Animation<Sprite>>.withAnchor(x: Double, y: Double) =
  withAnchor(constant(x), constant(y))

fun Live<Animation<Sprite>>.withAnchor(x: Live<Double>, y: Live<Double>) =
  with(anchor(x, y))

@JvmName("withAnchor")
fun Live<Animation<Sprite>>.with(anchor: Anchor<Double>): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(args[0], anchor.x, anchor.y, args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10])
  )
}

fun Live<Animation<Sprite>>.withPosition(x: Double, y: Double) =
  withPosition(constant(x), constant(y))

fun Live<Animation<Sprite>>.withPosition(x: Live<Double>, y: Live<Double>) =
  with(position(x, y))

@JvmName("withPosition")
fun Live<Animation<Sprite>>.with(position: Position<Double>): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(args[0], args[1], args[2], position.x, position.y, args[5], args[6], args[7], args[8], args[9], args[10])
  )
}

fun Live<Animation<Sprite>>.withFlip(x: Boolean, y: Boolean) =
  withFlip(constant(x), constant(y))

fun Live<Animation<Sprite>>.withFlip(x: Live<Boolean>, y: Live<Boolean>) =
  with(flip(x, y))

@JvmName("withFlip")
fun Live<Animation<Sprite>>.with(flip: Flip<Boolean>): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(args[0], args[1], args[2], args[3], args[4], flip.x, flip.y, args[7], args[8], args[9], args[10])
  )
}

fun Live<Animation<Sprite>>.withScale(x: Double, y: Double) =
  withScale(constant(x), constant(y))

fun Live<Animation<Sprite>>.withScale(x: Live<Double>, y: Live<Double>) =
  with(scale(x, y))

@JvmName("withScale")
fun Live<Animation<Sprite>>.with(scale: Scale<Double>): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], scale.x, scale.y, args[9], args[10])
  )
}

fun Live<Animation<Sprite>>.withComposite(composite: Composite) =
  withComposite(constant(Composite::class, composite))

fun Live<Animation<Sprite>>.withComposite(composite: Live<Composite>): LiveSprite =
  asApplication.run {
    Live.Application(
      kClass,
      primitive,
      listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], composite, args[9], args[10])
    )
  }

fun Live<Animation<Sprite>>.withParallax(parallax: Double) =
  withParallax(constant(parallax))

fun Live<Animation<Sprite>>.withParallax(parallax: Live<Double>): LiveSprite =
  asApplication.run {
    Live.Application(
      kClass,
      primitive,
      listOf(
        args[0],
        args[1],
        args[2],
        args[3],
        args[4],
        args[5],
        args[6],
        args[7],
        args[8],
        args[9],
        parallax,
        args[10]
      )
    )
  }

fun Live<Animation<Sprite>>.withAngle(degrees: Double) =
  withAngle(constant(degrees))

fun Live<Animation<Sprite>>.withAngle(degrees: Live<Double>) =
  with(angle(degrees))

@JvmName("withAngle")
fun Live<Animation<Sprite>>.with(angle: Angle): LiveSprite = asApplication.run {
  Live.Application(
    kClass,
    primitive,
    listOf(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9], angle.degrees)
  )
}

