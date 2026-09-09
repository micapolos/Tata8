package micapolos.zexy3.model

sealed class Number : Value<Number> {
  object FrameTime : Number()

  object MouseX : Number()

  object MouseY : Number()

  class Constant(val d: Double) : Number()

  class Add(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Sub(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Mul(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class FromInteger(val i: Value<Integer>) : Number()
}
