package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val x = variable(10)
  val animatedX = x.apply { capture(x + 1) }
  val y = variable(animatedX)
  y.show()
}