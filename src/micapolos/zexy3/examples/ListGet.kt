package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val list = listOf(
    "0---------",
    "-0--------",
    "--0-------",
    "---0------",
    "----0-----",
    "-----0----",
    "------0---",
    "-------0--",
    "--------0-",
    "---------0"
  )

  list[frame.count / 10 % 10].show()
}