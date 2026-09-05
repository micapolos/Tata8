package micapolos.ast

internal var nextId = 0

fun constant(i: Int): Expression<Int> =
  Expression.Constant(Int::class, i)

fun <T> variable(initializer: Expression<T>): Expression<T> {
  nextId++
  return Expression.Variable(Int::class, nextId, initializer)
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Unit> =
  Expression.Set(this, expression)

operator fun Expression<Int>.unaryMinus(): Expression<Int> =
  Expression.Application(kClass, this, "Int.unaryMinus", listOf())

operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, this, "Int.plus", listOf(expression))

fun fillRect(
  x: Expression<Int>,
  y: Expression<Int>,
  width: Expression<Int>,
  height: Expression<Int>
): Expression<Unit> =
  Expression.Application(Unit::class, null, "fillRect", listOf(x, y, width, height))

fun sequence(vararg expressions: Expression<*>): Expression<Unit> =
  Expression.Application(Unit::class, null, "sequence", expressions.asList())