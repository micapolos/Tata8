package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  variable(10).apply { capture(this + 1).everyFrame }.show()
}