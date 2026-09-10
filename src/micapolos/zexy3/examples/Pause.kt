package micapolos.zexy3.examples

import micapolos.zexy3.*

fun main() {
  sprite(image("/micapolos/quote.png"), 0, 0)
    .also { pause(5.0) }
    .show()
}