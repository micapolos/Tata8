package micapolos.zexy.examples

import micapolos.zexy.*

fun border(
  x: Value<Integer>,
  y: Value<Integer>,
  width: Value<Integer>,
  height: Value<Integer>,
) =
  stack(
    line.withStart(position(x + 1, y)).withEnd(position(x + width - 2, y)),
    line.withStart(position(x + 1, y + height - 1)).withEnd(position(x + width - 2, y + height - 1)),
    line.withStart(position(x, y + 1)).withEnd(position(x, y + height - 2)),
    line.withStart(position(x + width - 1, y + 1)).withEnd(position(x + width - 1, y + height - 2))
  )

fun bubble(
  x: Value<Integer>,
  y: Value<Integer>,
  width: Value<Integer>,
  height: Value<Integer>,
) =
  stack(
    border(x, y, width, height)
      .with(color.black.withAlpha(0.85)),
    border(x + 1, y + 1, width - 2, height - 2)
      .with(color.yellow),
    rect
      .with(position(x + 2, y + 2))
      .with(size(width - 4, height - 4))
      .with(color.withRed(0.1).withGreen(0.0).withBlue(0.15).withAlpha(0.78))
  )


fun main() {
  val font = font("/micapolos/tata8/mica-font.png")

  val text = """
    Hello, my friend,
    My name is Michal.
    I'm very old and tired.
    How are you?
  """.trimIndent()

  val x = 30.value
  val y = 10.value

  val textWidth = font.width(text)
  val textHeight = font.height(text)

  val margin = 3.value
  val width = textWidth + margin * 2 + 4
  val height = textHeight + margin * 2 + 4

  stack(
    sprite
      .with(image("/micapolos/depressedChicken.png"))
      .with(position(10, 15)),
    bubble(x, y, width - 1, height - 1),
    label
      .with(text)
      .with(position(x + margin + 2, y + margin + 2))
      .with(color.yellow)
      .with(font)
  ).show()
}