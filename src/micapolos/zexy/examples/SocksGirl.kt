package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  show {
    val direction = variable(4)
    val isTalking = key.z.isPressed
    val isMoving = key.x.isPressed
    val frameIndex = variable(0)

    val directionBaseIndex = direction.selectFrom(0, 7, 12, 17, 22, 27, 32, 37)
    val directionFrameCount = direction.selectFrom(6, 4, 4, 4, 4, 4, 4, 4)

    val spriteIndex = isTalking
      .ifTrue(frameIndex)
      .orElse(
        4.value + directionBaseIndex +
            isMoving
              .ifTrue(frameIndex + 1)
              .orElse(0)
      )

    val spriteFrameCount =
      isTalking
        .ifTrue(4)
        .orElse(
          isMoving
            .ifTrue(directionFrameCount)
            .orElse(1)
        )

    val girlSize = size(64, 64)

    repeat {
      frameIndex set (frameIndex + 1) % spriteFrameCount
      this pause 0.15
    }

    startOn(key.z.press) {
      direction set 4
    }

    startOn(key.right.press) {
      direction set (direction + 1) % 8
    }

    startOn(key.left.press) {
      direction set (direction + 7) % 8
    }

    sprite
      .with(image("/micapolos/socksgirl-sheet.png"))
      .with(position((screen.size.width - 64) / 2, 160))
      .with(girlSize)
      .withImage(position(spriteIndex * girlSize.width, 0))
  }
}