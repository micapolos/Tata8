package micapolos.zexy2

import micapolos.zexy2.ast.Expression

class Animation<out T>

fun inParallel(expression: Expression<Animation<*>>, vararg expressions: Expression<Animation<*>>) =
  inParallel(listOf(expression, *expressions))

fun inParallel(expressions: List<Expression<Animation<*>>>) =
  Expression.Application<Animation<*>>(Animation::class, "parallel", expressions)

fun inSequence(expression: Expression<Animation<*>>, vararg expressions: Expression<Animation<*>>) =
  inSequence(listOf(expression, *expressions))

fun inSequence(expressions: List<Expression<Animation<*>>>) =
  Expression.Application<Animation<*>>(Animation::class, "sequence", expressions)
