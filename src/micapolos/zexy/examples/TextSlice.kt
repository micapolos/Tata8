package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val text = """
    This is a very long text,
    and it will slowly reveal itself.
    How do you like it?
  """.trimIndent()

  text.slice(0, frame.count.div(5).max(text.length)).show()
}