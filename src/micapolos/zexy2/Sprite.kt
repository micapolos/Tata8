package micapolos.zexy2

import micapolos.tata8.Composite
import micapolos.tata8.Image
import micapolos.tata8.Sprite
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import micapolos.zexy2.ast.withArg

val sprite: Live<Animation<Sprite>>
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

fun Live<Animation<Sprite>>.with(image: Image) =
  with(constant(Image::class, image))

fun Live<Animation<Sprite>>.with(image: Live<Image>) = withArg(0, image)

fun Live<Animation<Sprite>>.withAnchor(x: Double, y: Double) =
  withAnchor(constant(x), constant(y))

fun Live<Animation<Sprite>>.withAnchor(x: Live<Double>, y: Live<Double>) =
  with(anchor(x, y))

@JvmName("withAnchor")
fun Live<Animation<Sprite>>.with(anchor: Anchor<Double>) = withArg(1, anchor.x).withArg(2, anchor.y)

fun Live<Animation<Sprite>>.withPosition(x: Double, y: Double) =
  withPosition(constant(x), constant(y))

fun Live<Animation<Sprite>>.withPosition(x: Live<Double>, y: Live<Double>) =
  with(position(x, y))

@JvmName("withPosition")
fun Live<Animation<Sprite>>.with(position: Position<Double>) = withArg(3, position.x).withArg(4, position.y)

fun Live<Animation<Sprite>>.withFlip(x: Boolean, y: Boolean) =
  withFlip(constant(x), constant(y))

fun Live<Animation<Sprite>>.withFlip(x: Live<Boolean>, y: Live<Boolean>) =
  with(flip(x, y))

@JvmName("withFlip")
fun Live<Animation<Sprite>>.with(flip: Flip<Boolean>) = withArg(5, flip.x).withArg(6, flip.y)

fun Live<Animation<Sprite>>.withScale(x: Double, y: Double) =
  withScale(constant(x), constant(y))

fun Live<Animation<Sprite>>.withScale(x: Live<Double>, y: Live<Double>) =
  with(scale(x, y))

@JvmName("withScale")
fun Live<Animation<Sprite>>.with(scale: Scale<Double>) = withArg(7, scale.x).withArg(8, scale.y)

fun Live<Animation<Sprite>>.withComposite(composite: Composite) =
  withComposite(constant(Composite::class, composite))

fun Live<Animation<Sprite>>.withComposite(composite: Live<Composite>) = withArg(9, composite)

fun Live<Animation<Sprite>>.withAngle(degrees: Live<Double>) =
  with(angle(degrees))

@JvmName("withAngle")
fun Live<Animation<Sprite>>.with(angle: Angle) = withArg(10, angle.degrees)