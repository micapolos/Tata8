package micapolos.zexy2

import micapolos.zexy2.ast.Expression

fun animation(vararg expressions: Expression<*>): Expression<Unit> =
  Expression.Application(Unit::class, "sequence", expressions.asList())
