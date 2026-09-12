package micapolos.zexy3.examples

import micapolos.zexy3.frame
import micapolos.zexy3.mouse
import micapolos.zexy3.show
import micapolos.zexy3.startOn

fun main() {
  frame.count.startOn(mouse.press).show()
}