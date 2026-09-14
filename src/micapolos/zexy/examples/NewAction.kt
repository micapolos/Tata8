package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  action {
    val x = variable(0)
    x set 10.value
    x capture x + 1
    x select {
      x.set(20.value)
      x.set(30.value)
    }
    sequence {
      x.set(10.value)
      x.capture(x + 1)
    }
  }
}