package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Animation<out T>

fun parallel(expression: Expression<Animation<*>>, vararg expressions: Expression<Animation<*>>) =
  parallel(listOf(expression, *expressions))

fun parallel(expressions: List<Expression<Animation<*>>>) =
  Expression.Application<Animation<*>>(Animation::class, "parallel", expressions)

fun sequence(expression: Expression<Animation<*>>, vararg expressions: Expression<Animation<*>>) =
  sequence(listOf(expression, *expressions))

fun sequence(expressions: List<Expression<Animation<*>>>) =
  Expression.Application<Animation<*>>(Animation::class, "sequence", expressions)
