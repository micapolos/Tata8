package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    val x = variable(0)
    pause(10.0)

    once {
      x bind2 10.value
      x set2 x + 1
    }

    parallel {
      micapolos.zexy.pause(10.0)
      micapolos.zexy.pause(20.0)
    }

    race {
      micapolos.zexy.pause(10.0)
      micapolos.zexy.pause(20.0)
    }

    sequence {
      micapolos.zexy.pause(10.0)
      micapolos.zexy.pause(20.0)
    }

    x selectStart {
      micapolos.zexy.pause(10.0)
      micapolos.zexy.pause(20.0)
    }

    x selectStep {
      micapolos.zexy.pause(10.0)
      micapolos.zexy.pause(20.0)
    }
  }
}