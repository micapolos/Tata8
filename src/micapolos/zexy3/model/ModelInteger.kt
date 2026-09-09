package micapolos.zexy3.model

sealed class ModelInteger : Value<ModelInteger> {
  object ScreenWidth : ModelInteger()

  object ScreenHeight : ModelInteger()

  object MouseDown : ModelInteger()

  class KeyDown(val key: Key) : ModelInteger()

  class ImageWidth(val image: Value<Image>) : ModelInteger()

  class ImageHeight(val image: Value<Image>) : ModelInteger()

  class Constant(val i: Int) : ModelInteger()

  class Add(val lhs: Value<ModelInteger>, val rhs: Value<ModelInteger>) : ModelInteger()

  class Sub(val lhs: Value<ModelInteger>, val rhs: Value<ModelInteger>) : ModelInteger()

  class Mul(val lhs: Value<ModelInteger>, val rhs: Value<ModelInteger>) : ModelInteger()

  class IntegerEq(val lhs: Value<ModelInteger>, val rhs: Value<ModelInteger>) : ModelInteger()

  class IntegerLt(val lhs: Value<ModelInteger>, val rhs: Value<ModelInteger>) : ModelInteger()

  class NumberEq(val lhs: Value<Number>, val rhs: Value<Number>) : ModelInteger()

  class NumberLt(val lhs: Value<Number>, val rhs: Value<Number>) : ModelInteger()

  class RisingEdge(val condition: Value<ModelInteger>) : ModelInteger()

  class FromNumber(val number: Value<Number>) : ModelInteger()
}
