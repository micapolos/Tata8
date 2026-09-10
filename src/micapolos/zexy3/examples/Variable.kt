package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  animatedVariable(10.value) { it.capture(it + 1) }.show()
}