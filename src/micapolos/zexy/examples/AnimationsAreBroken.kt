package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val frameCount = variable(0).also { it.add(1).everyFrame }
  val frameCount2 = (frameCount + frameCount + frameCount + frameCount +frameCount + frameCount + frameCount + frameCount).div(8)
  // This should run at x1 speed, and not x8 speed!!!
  frameCount2.div(60).show()
}