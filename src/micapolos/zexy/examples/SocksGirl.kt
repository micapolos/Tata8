package micapolos.zexy.examples

import micapolos.zexy.*

fun directionIndex(direction: Value<Integer>): Value<Integer> =
  direction.selectFrom(0, 7, 12, 17, 22, 27, 32, 37)

fun directionSteps(direction: Value<Integer>): Value<Integer> =
  direction.selectFrom(6, 4, 4, 4, 4, 4, 4, 4)

fun index(
  isTalking: Value<Bool>,
  isMoving: Value<Bool>,
  direction: Value<Integer>,
  step: Value<Integer>
): Value<Integer> =
  isTalking
    .ifTrue(step)
    .orElse(
      4.value + directionIndex(direction) +
          isMoving
            .ifTrue(step + 1)
            .orElse(0)
    )

fun steps(isTalking: Value<Bool>, isMoving: Value<Bool>, direction: Value<Integer>): Value<Integer> =
  isTalking
    .ifTrue(4)
    .orElse(
      isMoving
        .ifTrue(directionSteps(direction))
        .orElse(1)
    )

fun main() {
  val direction = variable(0)
  val isTalking = key.z.isPressed
  val isMoving = key.x.isPressed
  val step = variable(0)

  val imageIndex = index(isTalking, isMoving, direction, step)

  val girlSize = size(64, 64)
  sprite
    .with(image("/micapolos/socksgirl-sheet.png"))
    .with(position((screen.size.width - 64) / 2, 160))
    .with(girlSize)
    .withImage(position(imageIndex * girlSize.width, 0))
    .withAnimation {
      repeat {
        step set (step + 1) % steps(isTalking, isMoving, direction)
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