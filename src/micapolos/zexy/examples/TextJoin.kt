package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  val names = texts("Michal", "Marysia", "Kornelka", "Mikolaj", "Amelka")

  val hello = join(
    "Hello, ".text,
    names[frame.count.div(30).rem(names.size)],
    "!!!".text
  )

  hello.show()
}