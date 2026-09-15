package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val direction = variable(4)
  val isTalking = key.z.isPressed
  val isMoving = key.x.isPressed
  val step = variable(0)

  val directionBaseIndex = direction.selectFrom(0, 7, 12, 17, 22, 27, 32, 37)
  val directionSteps = direction.selectFrom(6, 4, 4, 4, 4, 4, 4, 4)

  val spriteIndex = isTalking
    .ifTrue(step)
    .orElse(
      4.value + directionBaseIndex +
          isMoving
            .ifTrue(step + 1)
            .orElse(0)
    )

  val spriteSteps =
    isTalking
      .ifTrue(4)
      .orElse(
        isMoving
          .ifTrue(directionSteps)
          .orElse(1)
      )

  val girlSize = size(64, 64)
  sprite
    .with(image("/micapolos/socksgirl-sheet.png"))
    .with(position((screen.size.width - 64) / 2, 160))
    .with(girlSize)
    .withImage(position(spriteIndex * girlSize.width, 0))
    .withAnimation {
      repeat {
        step set (step + 1) % spriteSteps
        this pause 0.15
        key.right.isPressed.integer.selectStart {
          doNothing
          direction set (direction + 1) % 8
        }
        key.left.isPressed.integer.selectStart {
          doNothing
          direction set (direction + 7) % 8
        }
      }
    }
    .show()
}