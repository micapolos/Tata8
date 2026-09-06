package micapolos.ast

fun animation(vararg expressions: Expression<*>): Expression<Unit> =
  Expression.Application(Unit::class, "sequence", expressions.asList())
