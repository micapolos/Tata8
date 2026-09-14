package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    parallel {
      frame.count2.div(10).change2.show2()
    }
  }
}