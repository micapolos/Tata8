package micapolos.zexy3

sealed class Bool {
  class Constant(b: Boolean) : Bool()
  class Variable(val initial: Bool): Bool()

  class Not(val bool: Bool): Bool()
  class And(val a: Bool, val b: Bool): Bool()
  class Or(val a: Bool, val b: Bool): Bool()

  object MousePressed: Bool()
  class KeyPressed(val key: Key): Bool()

  class IndexEqual(val a: Integer, val b: Integer): Bool()
  class NumberEqual(val a: Number, val b: Number): Bool()
}