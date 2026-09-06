package micapolos.zexy2

import micapolos.zexy2.ast.Expression

fun parallel(expression: Expression<*>, vararg expressions: Expression<*>) =
  parallel(listOf(expression, *expressions))

fun parallel(expressions: List<Expression<*>>) =
  Expression.Application<Unit>(Unit::class, "parallel", expressions)

fun sequence(expression: Expression<*>, vararg expressions: Expression<*>) =
  sequence(listOf(expression, *expressions))

fun sequence(expressions: List<Expression<*>>) =
  Expression.Application<Unit>(Unit::class, "sequence", expressions)
