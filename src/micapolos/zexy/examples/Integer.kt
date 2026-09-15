package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  animation {
    once {
      1.value logAs "1"
      0.value.isNotZero logAs "0 is not zero"
      1.value.isNotZero logAs "1 is not zero"
      10.value.isNotZero logAs "10 is not zero"
      2.value + 3.value logAs "2 + 3"
      3.value - 2.value logAs "3 - 1"
      2.value * 3.value logAs "2 * 3"
      6.value / 3.value logAs "6 / 3"
      7.value % 2.value logAs "7 % 2"
      2.value.isEqualTo(2.value) logAs "2 == 2"
      2.value.isEqualTo(3.value) logAs "2 == 3"
      "Game".show()
    }
  }
}