package micapolos.zexy.examples

import micapolos.zexy.frame
import micapolos.zexy.mouse
import micapolos.zexy.show
import micapolos.zexy.startOn

fun main() {
  frame.count.startOn(mouse.press).show()
}