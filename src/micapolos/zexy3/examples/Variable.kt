package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val x = variable(10)
  x.also(x.set(x + 1).everyFrame).logged.show()
}