package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  variable(0) {
    it.add(1).then(pause(1.0))
  }.show()
}