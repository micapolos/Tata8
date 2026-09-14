package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  variable(0)
    .animate { counter ->
      parallel {
        everyFrame {
          counter add2 1
        }
        counter.rem(60)
          .loggedAs("counter % 60")
          .isEqualTo(0)
          .ifTrue(0)
          .orElse(1)
          .loggedAs("trigger")
          .selectStart {
            counter set2 0
            sequence { }
          }
      }
    }
    .show()
}