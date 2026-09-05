package micapolos.ast

import micapolos.tata8.Game

internal var nextId = 0

fun constant(i: Int): Expression<Int> =
  Expression.Constant(Int::class, i)

fun variable(i: Int): Expression<Int> = variable(constant(i))

fun <T> variable(initializer: Expression<T>): Expression<T> {
  nextId++
  return Expression.Variable(Int::class, nextId, initializer)
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Unit> =
  Expression.Set(this, expression)

operator fun Expression<Int>.plus(i: Int): Expression<Int> = plus(constant(i))

operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.plus", listOf(this, expression))

operator fun Expression<Int>.minus(i: Int): Expression<Int> = minus(constant(i))

operator fun Expression<Int>.minus(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.minus", listOf(this, expression))

operator fun Expression<Int>.times(i: Int): Expression<Int> = plus(constant(i))

operator fun Expression<Int>.times(expression: Expression<Int>): Expression<Int> =
  Expression.Application(kClass, "Int.times", listOf(this, expression))

fun fillRect(x: Int, y: Int, width: Int, height: Int) =
  fillRect(constant(x), constant(y), width, height)

fun fillRect(x: Expression<Int>, y: Expression<Int>, width: Int, height: Int) =
  fillRect(x, y, constant(width), constant(height))

fun fillRect(
  x: Expression<Int>,
  y: Expression<Int>,
  width: Expression<Int>,
  height: Expression<Int>
): Expression<Unit> =
  Expression.Application(Unit::class, "fillRect", listOf(x, y, width, height))

fun animation(vararg expressions: Expression<*>): Expression<Unit> =
  Expression.Application(Unit::class, "sequence", expressions.asList())

fun Expression<Int>.keepAdding(i: Int): Expression<Unit> = keepAdding(constant(i))

fun Expression<Int>.keepAdding(expression: Expression<Int>): Expression<Unit> =
  Expression.Application(Unit::class, "Int.keepAdding", listOf(this, expression))

val screenWidth = constant(Game.WIDTH)
val screenHeight = constant(Game.HEIGHT)