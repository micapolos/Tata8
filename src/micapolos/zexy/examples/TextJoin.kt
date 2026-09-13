package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val nameTexts = listOf("Michal", "Marysia", "Kornelka", "Mikolaj", "Amelka")

  val helloText = join(
    "Hello, ".value,
    nameTexts[frame.count.div(30).rem(nameTexts.size)],
    "!!!".value
  )

  helloText.show()
}