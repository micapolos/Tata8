package micapolos.zexy2

import micapolos.tata8.Composite
import micapolos.tata8.Image
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.withArg

object DrawSprite: Draw

val drawSprite: Live<DrawSprite>
  get() =
    Live.Application(
      DrawSprite::class,
      Primitive.SPRITE,
      listOf(
        null.live(Image::class),
        0.0.live, 0.0.live,
        0.0.live, 0.0.live,
        false.live, false.live,
        1.0.live, 1.0.live,
        Composite.NORMAL.live,
        0.0.live,
        1.0.live
      )
    )

fun Live<DrawSprite>.with(image: Image) = with(image.live)

fun Live<DrawSprite>.with(image: Live<Image>) = withArg(0, image)

fun Live<DrawSprite>.withAlignment(x: Double, y: Double) =
  withAlignment(x.live, y.live)

fun Live<DrawSprite>.withAlignment(x: Live<Double>, y: Live<Double>) =
  with(alignment(x, y))

@JvmName("withAnchor")
fun Live<DrawSprite>.with(alignment: Alignment<Double>) = withArg(1, alignment.x).withArg(2, alignment.y)

fun Live<DrawSprite>.withPosition(x: Double, y: Double) =
  withPosition(x.live, y.live)

fun Live<DrawSprite>.withPosition(x: Live<Double>, y: Live<Double>) =
  with(position(x, y))

@JvmName("withPosition")
fun Live<DrawSprite>.with(position: Position<Double>) = withArg(3, position.x).withArg(4, position.y)

fun Live<DrawSprite>.withFlip(x: Boolean, y: Boolean) =
  withFlip(x.live, y.live)

fun Live<DrawSprite>.withFlip(x: Live<Boolean>, y: Live<Boolean>) =
  with(flip(x, y))

@JvmName("withFlip")
fun Live<DrawSprite>.with(flip: Flip<Boolean>) = withArg(5, flip.x).withArg(6, flip.y)

fun Live<DrawSprite>.withScale(x: Double, y: Double) =
  withScale(x.live, y.live)

fun Live<DrawSprite>.withScale(x: Live<Double>, y: Live<Double>) =
  with(scale(x, y))

@JvmName("withScale")
fun Live<DrawSprite>.with(scale: Scale<Double>) = withArg(7, scale.x).withArg(8, scale.y)

fun Live<DrawSprite>.withComposite(composite: Composite) =
  withComposite(composite.live)

fun Live<DrawSprite>.withComposite(composite: Live<Composite>) = withArg(9, composite)

fun Live<DrawSprite>.withAngle(degrees: Live<Double>) =
  with(angle(degrees))

@JvmName("withAngle")
fun Live<DrawSprite>.with(angle: Angle) = withArg(10, angle.degrees)

@JvmName("withParallaxRatio")
fun Live<DrawSprite>.with(parallax: Parallax) = withArg(11, parallax.ratio)