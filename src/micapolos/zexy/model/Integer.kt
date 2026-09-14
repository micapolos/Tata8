package micapolos.zexy.model

sealed class Integer : Value<Integer> {
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

  data class Constant(val i: Int) : Integer()

  data class Apply0(val op: Op0) : Integer()

  data class Apply1(val op: Op1, val integer: Value<Integer>) : Integer()

  data class Apply2(val op: Op2, val lhs: Value<Integer>, val rhs: Value<Integer>) : Integer()

  data class KeyDown(val key: Key) : Integer()

  data class ImageWidth(val image: Value<Image>) : Integer()

  data class ImageHeight(val image: Value<Image>) : Integer()

  data class TextLength(val text: Value<Text>) : Integer()

  data class TextWidth(val text: Value<Text>, val font: Value<Font>) : Integer()

  data class TextHeight(val text: Value<Text>, val font: Value<Font>) : Integer()

  data class FromNumber(val number: Value<Number>) : Integer()
}
