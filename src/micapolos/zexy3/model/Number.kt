package micapolos.zexy3.model

sealed class Number : Value<Number> {
  enum class Op0 {
    FRAME_TIME,
  }

  enum class Op1 {
    NEG,
    SIN,
    COS,
    ABS,
    SQRT,
    FLOOR,
    CEIL,
    ROUND,
    FRACT,
  }

  enum class Op2 {
    ADD,
    SUB,
    MUL,
  }

  enum class NumberPred2 {
    EQ,
    LT,
  }

  class Constant(val d: Double) : Number()

  class Apply0(val op: Op0) : Number()

  class Apply1(val op: Op1, val n: Value<Number>) : Number()

  class Apply2(val op: Op2, val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  class Test2(val pred: NumberPred2, val lhs: Value<Number>, val rhs: Value<Number>) : Integer()

  class FromInteger(val i: Value<Integer>) : Number()
}
