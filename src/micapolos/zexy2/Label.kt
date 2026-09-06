package micapolos.zexy2

import micapolos.tata8.Color
import micapolos.tata8.Font
import micapolos.zexy2.ast.Expression

object Label

val label get() =
  Expression.Application<Animation<Label>>(
    Animation::class,
    "label",
    listOf(
      constant(String::class, ""),
      constant(0.0),
      constant(0.0),
      constant(Color::class, Color.WHITE),
      constant(Font::class, Font.system)))

fun Expression.Application<Animation<Label>>.with(string: String) =
  with(constant(String::class, string))

@JvmName("withString")
fun Expression.Application<Animation<Label>>.with(string: Expression<String>) =
  Expression.Application<Animation<Label>>(kClass, name, listOf(string, args[1], args[2], args[3], args[4]))

fun Expression.Application<Animation<Label>>.with(position: Position<Double>) =
  Expression.Application<Animation<Label>>(kClass, name, listOf(args[0], position.x, position.y, args[3], args[4]))

fun Expression.Application<Animation<Label>>.with(color: Color) =
  with(constant(Color::class, color))

@JvmName("withColor")
fun Expression.Application<Animation<Label>>.with(color: Expression<Color>) =
  Expression.Application<Animation<Label>>(kClass, name, listOf(args[0], args[1], args[2], color, args[4]))

fun Expression.Application<Animation<Label>>.with(font: Font) =
  with(constant(Font::class, font))

@JvmName("withFont")
fun Expression.Application<Animation<Label>>.with(font: Expression<Font>) =
  Expression.Application<Animation<Label>>(kClass, name, listOf(args[0], args[1], args[2], args[3], font))
