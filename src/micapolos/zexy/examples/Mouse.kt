package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  parallel(
    mouse.position.x.loggedAs("x"),
    mouse.position.y.loggedAs("y"),
    mouse.isPressed.loggedAs("is pressed"),
    mouse.press.loggedAs("press"),
    mouse.release.loggedAs("release"),
  ).show()
}

