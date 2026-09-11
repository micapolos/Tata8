package micapolos.zexy3.examples

import micapolos.zexy3.*


fun main() {
  variable(0).also {
   sequence(
     it.add(10),
     it.add(20),
     it.add(30))
  }.show()
}