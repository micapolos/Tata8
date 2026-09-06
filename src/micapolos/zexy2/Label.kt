package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import micapolos.zexy2.ast.withArg

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
        Color.WHITE.live(Color::class),
        Game.font.live
      )
    )

fun Live<Animation<Label>>.with(string: String) =
  with(string.live)

@JvmName("withString")
fun Live<Animation<Label>>.with(string: Live<String>) = withArg(0, string)

fun Live<Animation<Label>>.with(position: Position<Double>) = withArg(1, position.x).withArg(2, position.y)

fun Live<Animation<Label>>.with(color: Color) =
  with(color.live(Color::class))

@JvmName("withColor")
fun Live<Animation<Label>>.with(color: Live<Color>) = withArg(3, color)

fun Live<Animation<Label>>.with(font: Font) = with(font.live)

@JvmName("withFont")
fun Live<Animation<Label>>.with(font: Live<Font>) = withArg(4, font)
