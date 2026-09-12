package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val step = (frame.count / 10 % 10)
  val text = step.selectFrom(
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