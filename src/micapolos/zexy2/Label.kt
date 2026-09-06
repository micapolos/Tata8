package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.asApplication

object Label

typealias LiveLabel = Live<Animation<Label>>

val label: LiveLabel get() =
  Live.Application(
    Animation::class,
    "label",
    listOf(
      constant(String::class, ""),
      constant(0.0),
      constant(0.0),
      constant(Color::class, Color.WHITE),
      constant(Font::class, Game.font)))

fun Live<Animation<Label>>.with(string: String) =
  with(constant(String::class, string))

@JvmName("withString")
fun Live<Animation<Label>>.with(string: Live<String>): LiveLabel = asApplication.run {
  Live.Application(kClass, name, listOf(string, args[1], args[2], args[3], args[4]))
}

fun Live<Animation<Label>>.with(position: Position<Double>): LiveLabel = asApplication.run {
  Live.Application(kClass, name, listOf(args[0], position.x, position.y, args[3], args[4]))
}

fun Live<Animation<Label>>.with(color: Color) =
  with(constant(Color::class, color))

@JvmName("withColor")
fun Live<Animation<Label>>.with(color: Live<Color>): LiveLabel = asApplication.run {
  Live.Application(kClass, name, listOf(args[0], args[1], args[2], color, args[4]))
}

fun Live<Animation<Label>>.with(font: Font) =
  with(constant(Font::class, font))

@JvmName("withFont")
fun Live<Animation<Label>>.with(font: Live<Font>): LiveLabel = asApplication.run {
  Live.Application(kClass, name, listOf(args[0], args[1], args[2], args[3], font))
}