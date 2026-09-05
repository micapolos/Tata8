package micapolos.ast

fun main() {
  sequence(
    fillRect(constant(10), constant(10), constant(30), constant(30)),
    fillRect(constant(50), constant(70), constant(70), constant(60)))
    .show()
}