package micapolos.zexy3.model

sealed class Integer : Value<Integer> {
  enum class Op0 {
    SCREEN_WIDTH,
    SCREEN_HEIGHT,
    MOUSE_DOWN,
  }

  enum class Op1 {
    NEG,
    CHANGE,
  }

  enum class Op2 {
    ADD,
    SUB,
    MUL,
    EQ,
    LT,
  }

  class Constant(val i: Int) : Integer()

  class Apply0(val op: Op0): Integer()

  class Apply1(val op: Op1, val integer: Value<Integer>): Integer()

  class Apply2(val op: Op2, val lhs: Value<Integer>, val rhs: Value<Integer>): Integer()

  class KeyDown(val key: Key) : Integer()

  class ImageWidth(val image: Value<Image>) : Integer()

  class ImageHeight(val image: Value<Image>) : Integer()

  class TextWidth(val text: Value<Text>) : Integer()

  class TextHeight(val text: Value<Text>) : Integer()

  class FromNumber(val number: Value<Number>) : Integer()
}
