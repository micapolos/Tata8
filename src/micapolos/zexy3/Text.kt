package micapolos.zexy3

sealed class Text {
  class Constant(val string: String): Text()
  class Variable(val initial: Text): Text()
}