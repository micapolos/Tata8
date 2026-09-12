package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(10).apply { capture(this + 1) }.show()
}