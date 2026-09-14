package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val x = variable(0)
    pause(10)
    instant {
      x.set(10.value)
      x.capture(x + 1)
    }
    parallel {
      pause(10.0)
      pause(20.0)
    }
    race {
      pause(10.0)
      pause(20.0)
    }
    sequence {
      pause(10.0)
      pause(20.0)
    }
    x.selectStart {
      pause(10.0)
      pause(20.0)
    }
    x.selectStep {
      pause(10.0)
      pause(20.0)
    }
  }
}