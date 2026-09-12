package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(0) { it
    .add(1)
    .then(pause(1.0))
    .repeatWhile(it.isLessThan(5))
  }.show()
}