package micapolos.zexy3.indexed

sealed class Integer: Value<Integer> {
  enum class Op0 {
    SCREEN_WIDTH,
    SCREEN_HEIGHT,
    MOUSE_DOWN,
    MOUSE_X,
    MOUSE_Y,
  }

  enum class Op1 {
    NEG,
  }

  enum class Op2 {
    ADD,
    SUB,
    MUL,
    DIV,
    REM,
    EQ,
    LT,
    AND,
    OR,
    XOR,
  }

  class Constant(val i: Int) : Integer()

  class Apply0(val op: Op0): Integer()

  class Apply1(val op: Op1, val integer: Value<Integer>): Integer()

  class Apply2(val op: Op2, val lhs: Value<Integer>, val rhs: Value<Integer>): Integer()

  class KeyDown(val key: Key) : Integer()

  class ImageWidth(val image: Value<Image>) : Integer()

  class ImageHeight(val image: Value<Image>) : Integer()

  class TextWidth(val text: Value<Text>, val font: Value<Font>) : Integer()

  class TextHeight(val text: Value<Text>, val font: Value<Font>) : Integer()

  class FromNumber(val number: Value<Number>) : Integer()
}