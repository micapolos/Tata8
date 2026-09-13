package micapolos.zexy.examples

import micapolos.zexy.*

fun main() {
  parallel(
    1.value.loggedAs("1"),
    0.value.isNotZero.loggedAs("0 is not zero"),
    1.value.isNotZero.loggedAs("1 is not zero"),
    10.value.isNotZero.loggedAs("10 is not zero"),
    (2.value + 3.value).loggedAs("2 + 3"),
    (3.value - 2.value).loggedAs("3 - 1"),
    (2.value * 3.value).loggedAs("2 * 3"),
    (6.value / 3.value).loggedAs("6 / 3"),
    (7.value % 2.value).loggedAs("7 % 2"),
    2.value.isEqualTo(2.value).loggedAs("2 == 2"),
    2.value.isEqualTo(3.value).loggedAs("2 == 3"),
    frame.count.loggedAs("frame count"),
    frame.count.unaryMinus().loggedAs("minus frame count"),
    frame.count.div(10).loggedAs("frame count / 10"),
    frame.count.div(10).rem(10).loggedAs("frame count / 10 % 10"),
    frame.count.rem(100).isLessThan(50).loggedAs("frame count % 100 < 50 == 0"),
    frame.count.rem(10).isEqualTo(0).loggedAs("frame count % 10 == 0"),
  ).show()
}