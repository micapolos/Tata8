package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  showDrawing {
    val x = variable(screen.size.width / 2)
    val direction = variable(1)
    val speed = key.z.isPressed.ifTrue(8).orElse(2)

    everyStep {
      x add direction * speed

      ifTrue(x.isGreaterThan(screen.size.width * 3 / 4 - 32)) {
        direction set -1
      }

      ifTrue(x.isLessThan(screen.size.width * 1 / 4)) {
        direction set 1
      }
    }

    stack(
      label
        .with("Bouncing Quote.")
        .with(position(2, 2))
        .with(color.yellow),
      label
        .with("Press Z for more speed.")
        .with(position(2, 12))
        .with(color.yellow),
      sprite
        .with(image("/micapolos/quote.png"))
        .with(position(x, 100)))
  }
}