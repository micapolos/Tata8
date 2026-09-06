package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.withArg

object Label: Draw

val drawLabel: Live<Label>
  get() =
    Live.Application(
      Unit::class,
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

fun Live<Label>.with(string: String) =
  with(string.live)

@JvmName("withString")
fun Live<Label>.with(string: Live<String>) = withArg(0, string)

fun Live<Label>.with(alignment: Alignment<Double>) = withArg(1, alignment.x).withArg(2, alignment.y)

fun Live<Label>.with(position: Position<Double>) = withArg(3, position.x).withArg(4, position.y)

fun Live<Label>.with(color: Color) =
  with(color.live(Color::class))

@JvmName("withColor")
fun Live<Label>.with(color: Live<Color>) = withArg(5, color)

fun Live<Label>.with(font: Font) = with(font.live)

@JvmName("withFont")
fun Live<Label>.with(font: Live<Font>) = withArg(6, font)
