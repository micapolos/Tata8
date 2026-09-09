package micapolos.zexy3.model

sealed class Integer : Value<Integer> {
  object ScreenWidth : Integer()

  object ScreenHeight : Integer()

  object MouseDown : Integer()

  class KeyDown(val key: Key) : Integer()

  class ImageWidth(val image: Value<Image>) : Integer()

  class ImageHeight(val image: Value<Image>) : Integer()

  class Constant(val i: Int) : Integer()

  class Add(val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  class Sub(val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  class Mul(val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  class IntegerEq(val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  class IntegerLt(val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  class NumberEq(val lhs: Value<Number>, val rhs: Value<Number>) : Integer()

  class NumberLt(val lhs: Value<Number>, val rhs: Value<Number>) : Integer()

  class RisingEdge(val condition: Value<Integer>) : Integer()

  class FromNumber(val number: Value<Number>) : Integer()
}
