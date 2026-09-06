package micapolos.zexy2

import micapolos.zexy2.ast.Expression

fun constant(d: Double): Expression<Double> =
  Expression.Constant(Double::class, d)

fun variable(d: Double): Expression<Double> = variable(constant(d))

fun Expression<Double>.keepAdding(d: Double) = keepAdding(constant(d))

@JvmName("keepAddingDouble")
fun Expression<Double>.keepAdding(expression: Expression<Double>): Expression<Animation<Double>> =
  Expression.Application(Animation::class, "Double.keepAdding", listOf(variable, expression))

operator fun Expression<Double>.plus(i: Double): Expression<Double> = plus(constant(i))

@JvmName("plusDouble")
operator fun Expression<Double>.plus(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.plus", listOf(this, expression))

operator fun Expression<Double>.minus(d: Double): Expression<Double> = minus(constant(d))

@JvmName("minusDouble")
operator fun Expression<Double>.minus(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.minus", listOf(this, expression))

operator fun Expression<Double>.times(d: Double): Expression<Double> = minus(constant(d))

@JvmName("timesDouble")
operator fun Expression<Double>.times(expression: Expression<Double>): Expression<Double> =
  Expression.Application(kClass, "Double.times", listOf(this, expression))
