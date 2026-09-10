package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  parallel(
    mouse.position.x.loggedAs("x"),
    mouse.position.y.loggedAs("y"),
    mouse.isPressed.loggedAs("is pressed")).show()
}