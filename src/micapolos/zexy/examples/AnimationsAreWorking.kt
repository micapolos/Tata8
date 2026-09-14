package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val frameCount = variable(0)
  val frameCount2 =
    (frameCount + frameCount + frameCount + frameCount + frameCount + frameCount + frameCount + frameCount).div(8)
  val seconds = frameCount2.div(60)

  seconds
    .animated { everyFrame { frameCount capture frameCount + 1 } }
    .show()
}