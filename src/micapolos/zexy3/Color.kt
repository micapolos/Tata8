package micapolos.zexy3

sealed class Color : Value {
  object Black : Color()
  object Yellow : Color()
  class Variable(val initial: Color): Color()
}