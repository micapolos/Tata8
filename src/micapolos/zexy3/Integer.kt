package micapolos.zexy3

sealed class Integer {
  class Constant(val i: Int): Integer()
  class Variable(val initial: Integer): Integer()

  object ScreenWidth: Integer()
  object ScreenHeight: Integer()

  class Plus(val a: Integer, val b: Integer): Integer()
}

fun index(i: Int): Integer = Integer.Constant(i)

fun Integer.plus(i: Int): Integer = plus(index(i))
fun Integer.plus(integer: Integer): Integer = Integer.Plus(this, integer)
