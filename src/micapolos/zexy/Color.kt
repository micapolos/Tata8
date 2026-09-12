package micapolos.zexy

import micapolos.zexy.model.Color as ModelColor

class Color(model: ModelColor): Value<Color>(model)

internal val Value<Color>.modelColor get() = model as ModelColor.Rgba

val color = color(1.0, 1.0, 1.0, 1.0)

fun color(red: Double, green: Double, blue: Double, alpha: Double) =
  color(red.value, green.value, blue.value, alpha.value)

fun color(red: Value<Number>, green: Value<Number>, blue: Value<Number>, alpha: Value<Number>) =
  Color(
    ModelColor.Rgba(
      red.modelNumber,
      green.modelNumber,
      blue.modelNumber,
      alpha.modelNumber))

val Value<Color>.transparent get() = color(0.0, 0.0, 0.0, 0.0)
val Value<Color>.white get() = color(1.0, 1.0, 1.0, 1.0)
val Value<Color>.red get() = color(1.0, 0.0, 0.0, 1.0)
val Value<Color>.green get() = color(0.0, 1.0, 0.0, 1.0)
val Value<Color>.blue get() = color(1.0, 1.0, 1.0, 1.0)
val Value<Color>.yellow get() = color(1.0, 1.0, 0.0, 1.0)
val Value<Color>.black get() = color(0.0, 0.0, 0.0, 1.0)

fun Value<Color>.withRed(red: Double) = withRed(red.value)
fun Value<Color>.withRed(red: Value<Number>) =
  Color(
    ModelColor.Rgba(
      red.modelNumber,
      modelColor.green,
      modelColor.blue,
      modelColor.alpha))

fun Value<Color>.withGreen(green: Double) = withGreen(green.value)
fun Value<Color>.withGreen(green: Value<Number>) =
  Color(
    ModelColor.Rgba(
      modelColor.red,
      green.modelNumber,
      modelColor.blue,
      modelColor.alpha))

fun Value<Color>.withBlue(blue: Double) = withBlue(blue.value)
fun Value<Color>.withBlue(blue: Value<Number>) =
  Color(
    ModelColor.Rgba(
      modelColor.red,
      modelColor.green,
      blue.modelNumber,
      modelColor.alpha))

fun Value<Color>.withAlpha(alpha: Double) = withAlpha(alpha.value)
fun Value<Color>.withAlpha(alpha: Value<Number>) =
  Color(
    ModelColor.Rgba(
      modelColor.red,
      modelColor.green,
      modelColor.blue,
      alpha.modelNumber))
