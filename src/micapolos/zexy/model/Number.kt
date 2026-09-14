package micapolos.zexy.model

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
    CMP,
  }

  data class Constant(val d: Double) : Number()

  data class Apply0(val op: Op0) : Number()

  data class Apply1(val op: Op1, val n: Value<Number>) : Number()

  data class Apply2(val op: Op2, val lhs: Value<Number>, val rhs: Value<Number>) : Number()

  data class Test2(val pred: NumberPred2, val lhs: Value<Number>, val rhs: Value<Number>) : Integer()

  data class FromInteger(val i: Value<Integer>) : Number()
}
