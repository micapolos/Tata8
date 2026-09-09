package micapolos.zexy3

sealed class Number : Value<Number> {
  data object FrameTime : Number()
  data object MouseX : Number()
  data object MouseY : Number()

  class Constant(val d: Double) : Number()

  class Add(val lhs: Value<Number>, val rhs: Value<Number>) : Number()
  class Sub(val lhs: Value<Number>, val rhs: Value<Number>) : Number()
  class Mul(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class FromInteger(val i: Value<Integer>) : Number()
}
