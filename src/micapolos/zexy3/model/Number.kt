package micapolos.zexy3.model

sealed class Number : Value<Number> {
  object FrameTime : Number()

  object MouseX : Number()

  object MouseY : Number()

  class Constant(val d: Double) : Number()

  class Abs(val lhs: Value<Number>) : Number()

  class Floor(val lhs: Value<Number>) : Number()

  class Round(val lhs: Value<Number>) : Number()

  class Ceil(val lhs: Value<Number>) : Number()

  class Fract(val lhs: Value<Number>) : Number()

  class Add(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Sub(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Mul(val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Sin(val lhs: Value<Number>) : Number()

  class Cos(val lhs: Value<Number>) : Number()

  class Sqrt(val lhs: Value<Number>) : Number()

  class FromInteger(val i: Value<ModelInteger>) : Number()
}
