package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.withArg

object Label

val label: Live<Animation<Label>>
  get() =
    Live.Application(
      Animation::class,
      Primitive.LABEL,
      listOf(
        "".live,
        0.0.live,
        0.0.live,
        0.0.live,
        0.0.live,
        Color.WHITE.live(Color::class),
        Game.font.live
      )
    )

fun Live<Animation<Label>>.with(string: String) =
  with(string.live)

@JvmName("withString")
fun Live<Animation<Label>>.with(string: Live<String>) = withArg(0, string)

fun Live<Animation<Label>>.with(alignment: Alignment<Double>) = withArg(1, alignment.x).withArg(2, alignment.y)

fun Live<Animation<Label>>.with(position: Position<Double>) = withArg(3, position.x).withArg(4, position.y)

fun Live<Animation<Label>>.with(color: Color) =
  with(color.live(Color::class))

@JvmName("withColor")
fun Live<Animation<Label>>.with(color: Live<Color>) = withArg(5, color)

fun Live<Animation<Label>>.with(font: Font) = with(font.live)

@JvmName("withFont")
fun Live<Animation<Label>>.with(font: Live<Font>) = withArg(6, font)
