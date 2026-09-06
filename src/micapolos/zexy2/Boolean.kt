package micapolos.zexy2

import micapolos.zexy2.ast.Expression
import kotlin.reflect.KClass

fun Expression<Boolean>.ifTrue(b: Boolean) = ifTrue(constant(b))
fun Expression<Boolean>.ifTrue(i: Int) = ifTrue(constant(i))
fun Expression<Boolean>.ifTrue(d: Double) = ifTrue(constant(d))
fun <T> Expression<Boolean>.ifTrue(kClass: KClass<*>, t: T) = ifTrue(constant(kClass, t))
fun <T> Expression<Boolean>.ifTrue(trueExpression: Expression<T>) = IfTrue(this, trueExpression)
data class IfTrue<T>(val condition: Expression<Boolean>, val trueExpression: Expression<T>)

fun IfTrue<Boolean>.orElse(b: Boolean) = orElse(constant(b))
fun IfTrue<Int>.orElse(i: Int) = orElse(constant(i))
fun IfTrue<Double>.orElse(d: Double) = orElse(constant(d))
fun <T> IfTrue<T>.orElse(t: T) = orElse(constant(trueExpression.kClass, t))
fun <T> IfTrue<T>.orElse(falseExpression: Expression<T>) =
  Expression.Conditional(trueExpression.kClass, condition, trueExpression, falseExpression)

fun constant(b: Boolean): Expression<Boolean> =
  Expression.Constant(Boolean::class, b)

fun variable(b: Boolean): Expression<Boolean> = variable(constant(b))

