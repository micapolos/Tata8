package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(100).also {
    sequence(
      it.add(50).startOn(key.right.press),
      it.subtract(50).startOn(key.left.press)
    )
  }.elastic.show()
}