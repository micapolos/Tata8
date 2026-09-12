package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  mouse.isPressed.selectFrom("pressed", "released").show()
}
