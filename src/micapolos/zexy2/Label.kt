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
        constant(String::class, ""),
        constant(0.0),
        constant(0.0),
        constant(Color::class, Color.WHITE),
        constant(Font::class, Game.font)
      )
    )

fun Live<Animation<Label>>.with(string: String) =
  with(constant(String::class, string))

@JvmName("withString")
fun Live<Animation<Label>>.with(string: Live<String>) = withArg(0, string)

fun Live<Animation<Label>>.with(position: Position<Double>) = withArg(1, position.x).withArg(2, position.y)

fun Live<Animation<Label>>.with(color: Color) =
  with(constant(Color::class, color))

@JvmName("withColor")
fun Live<Animation<Label>>.with(color: Live<Color>) = withArg(3, color)

fun Live<Animation<Label>>.with(font: Font) =
  with(constant(Font::class, font))

@JvmName("withFont")
fun Live<Animation<Label>>.with(font: Live<Font>) = withArg(4, font)
