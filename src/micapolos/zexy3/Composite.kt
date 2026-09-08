package micapolos.zexy3

sealed class Composite {
  object Normal : Composite()
  object SoftLight : Composite()
  object Multiply : Composite()

  class Variable(val initial: Composite): Composite()
}