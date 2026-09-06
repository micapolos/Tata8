package micapolos.zexy2

import micapolos.tata8.Composite
import micapolos.tata8.Image
import micapolos.tata8.Sprite
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.withArg

val sprite: Live<Animation<Sprite>>
  get() =
    Live.Application(
      Animation::class,
      Primitive.SPRITE,
      listOf(
        null.live(Image::class),
        0.0.live, 0.0.live,
        0.0.live, 0.0.live,
        false.live, false.live,
        1.0.live, 1.0.live,
        Composite.NORMAL.live,
        0.0.live
      )
    )

fun Live<Animation<Sprite>>.with(image: Image) = with(image.live)

fun Live<Animation<Sprite>>.with(image: Live<Image>) = withArg(0, image)

fun Live<Animation<Sprite>>.withAnchor(x: Double, y: Double) =
  withAnchor(x.live, y.live)

fun Live<Animation<Sprite>>.withAnchor(x: Live<Double>, y: Live<Double>) =
  with(anchor(x, y))

@JvmName("withAnchor")
fun Live<Animation<Sprite>>.with(anchor: Anchor<Double>) = withArg(1, anchor.x).withArg(2, anchor.y)

fun Live<Animation<Sprite>>.withPosition(x: Double, y: Double) =
  withPosition(x.live, y.live)

fun Live<Animation<Sprite>>.withPosition(x: Live<Double>, y: Live<Double>) =
  with(position(x, y))

@JvmName("withPosition")
fun Live<Animation<Sprite>>.with(position: Position<Double>) = withArg(3, position.x).withArg(4, position.y)

fun Live<Animation<Sprite>>.withFlip(x: Boolean, y: Boolean) =
  withFlip(x.live, y.live)

fun Live<Animation<Sprite>>.withFlip(x: Live<Boolean>, y: Live<Boolean>) =
  with(flip(x, y))

@JvmName("withFlip")
fun Live<Animation<Sprite>>.with(flip: Flip<Boolean>) = withArg(5, flip.x).withArg(6, flip.y)

fun Live<Animation<Sprite>>.withScale(x: Double, y: Double) =
  withScale(x.live, y.live)

fun Live<Animation<Sprite>>.withScale(x: Live<Double>, y: Live<Double>) =
  with(scale(x, y))

@JvmName("withScale")
fun Live<Animation<Sprite>>.with(scale: Scale<Double>) = withArg(7, scale.x).withArg(8, scale.y)

fun Live<Animation<Sprite>>.withComposite(composite: Composite) =
  withComposite(composite.live)

fun Live<Animation<Sprite>>.withComposite(composite: Live<Composite>) = withArg(9, composite)

fun Live<Animation<Sprite>>.withAngle(degrees: Live<Double>) =
  with(angle(degrees))

@JvmName("withAngle")
fun Live<Animation<Sprite>>.with(angle: Angle) = withArg(10, angle.degrees)