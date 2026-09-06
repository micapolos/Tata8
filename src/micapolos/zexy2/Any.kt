package micapolos.zexy2

import micapolos.zexy2.ast.Expression
import kotlin.reflect.KClass

internal var nextId = 0

val <T> Expression<T>.variable: Expression.Variable<T> get() =
  this as? Expression.Variable<T> ?: error("Not a variable")

val <T> Expression<T>.logged get() =
  Expression.Application<T>(kClass, "logged", listOf(this))

fun <T> Expression<T>.loggedAs(name: String) =
  Expression.Application<T>(kClass, "logged", listOf(constant(String::class, name), this))

val <T> Expression<T>.readOnly get() =
  Expression.Application<T>(kClass, "readOnly", listOf(this))

fun <T> constant(kClass: KClass<*>, value: T): Expression<T> =
  Expression.Constant(kClass, value)

fun <T> variable(initializer: Expression<T>): Expression<T> {
  nextId++
  return Expression.Variable(Int::class, nextId, initializer)
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Unit> =
  Expression.Set(variable, expression)
