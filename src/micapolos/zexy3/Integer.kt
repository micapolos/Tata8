package micapolos.zexy3

sealed class Integer : Value<Integer> {
  data object ScreenWidth : Integer()
  data object ScreenHeight : Integer()

  data object MouseDown : Integer()

  data class KeyDown(val key: Key) : Integer()

  data class ImageWidth(val image: Value<Image>) : Integer()
  data class ImageHeight(val image: Value<Image>) : Integer()

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
