package micapolos.zexy3

sealed class Color {
  object Black : Color()
  class Variable(val initial: Color): Color()
}