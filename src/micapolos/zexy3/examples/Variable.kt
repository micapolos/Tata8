package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val x = variable(1)
  x.set(x + 1).everyFrame.show()
}