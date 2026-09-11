package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  variable(0) {
    pause(3.0).then(it.add(1))
  }.show()

}