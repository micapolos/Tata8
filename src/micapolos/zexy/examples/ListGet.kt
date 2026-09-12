package micapolos.zexy.examples

import micapolos.zexy.*

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