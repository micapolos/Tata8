package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val text = "This is a very long text, which will slowly reveal itself."

  text
    .slice(0, frame.count.div(5).max(text.length))
    .show()
}