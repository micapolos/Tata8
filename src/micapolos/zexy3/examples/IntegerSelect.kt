package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  val step = frame.count / 10 % 10
  val text = step.selectTrueFalse(
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
  text.show()
}