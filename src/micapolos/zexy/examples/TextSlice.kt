package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val string = "This is a very long string, which will slowly reveal itself."

  string
    .slice(0, frame.count.div(5).max(string.length))
    .show()
}