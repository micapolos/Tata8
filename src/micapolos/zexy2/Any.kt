package micapolos.zexy2

import micapolos.zexy2.ast.Live
import kotlin.reflect.KClass

internal var nextId = 0

val <T> Live<T>.variable: Live.Variable<T> get() =
  this as? Live.Variable<T> ?: error("Not a variable")

val <T> Live<T>.logged get() =
  Live.Application<T>(kClass, "logged", listOf(this))

fun <T> Live<T>.loggedAs(name: String) =
  Live.Application<T>(kClass, "logged", listOf(constant(String::class, name), this))

val <T> Live<T>.readOnly get() =
  Live.Application<T>(kClass, "readOnly", listOf(this))

fun <T> constant(kClass: KClass<*>, value: T): Live<T> =
  Live.Constant(kClass, value)

fun <T> variable(initializer: Live<T>): Live<T> {
  nextId++
  return Live.Variable(Int::class, nextId, initializer)
}

fun <T> Live<T>.set(live: Live<T>): Live<Unit> =
  Live.Set(variable, live)
