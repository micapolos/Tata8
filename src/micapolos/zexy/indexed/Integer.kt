package micapolos.zexy.indexed

sealed class Integer: Value {
  enum class Op0 {
    SCREEN_WIDTH,
    SCREEN_HEIGHT,
    MOUSE_DOWN,
    MOUSE_X,
    MOUSE_Y,
  }

  enum class Op1 {
    NEG,
    NOT_ZERO
  }

  enum class Op2 {
    ADD,
    SUB,
    MUL,
    DIV,
    REM,
    EQ,
    CMP,
    AND,
    OR,
    XOR,
  }

  class Constant(val i: Int) : Integer()

  class Apply0(val op: Op0): Integer()

  class Apply1(val op: Op1, val integer: Value): Integer()

  class Apply2(val op: Op2, val lhs: Value, val rhs: Value): Integer()

  class KeyDown(val key: Key) : Integer()

  class ImageWidth(val image: Value) : Integer()

  class ImageHeight(val image: Value) : Integer()

  class TextLength(val text: Value) : Integer()

  class TextWidth(val text: Value, val font: Value) : Integer()

  class TextHeight(val text: Value, val font: Value) : Integer()

  class FromNumber(val number: Value) : Integer()
}