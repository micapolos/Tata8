package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import kotlin.reflect.KClass

fun <T> T.live(kClass: KClass<*>) = Live.Constant(kClass, this)

val <T> Live<T>.variable: Live.Variable<T> get() =
  this as? Live.Variable<T> ?: error("Not a variable")

val <T> Live<T>.logged get() =
  Live.Application<T>(kClass, Primitive.LOGGED, listOf(this))

fun <T> Live<T>.loggedAs(name: String) =
  Live.Application<T>(kClass, Primitive.LOGGED, listOf(name.live, this))

val <T> Live<T>.readOnly get() =
  Live.Application<T>(kClass, Primitive.READONLY, listOf(this))

fun <T> newVariable(initializer: Live<T>): Live<T> =
  Live.Variable(Int::class, initializer)

fun <T> Live<T>.set(live: Live<T>): Live<Unit> =
  Live.Set(variable, live)

fun inParallel(vararg lives: Live<*>) =
  inParallel(lives.toList())

fun inParallel(lives: List<Live<*>>): Live<Unit> =
  Live.Application(Unit::class, Primitive.PARALLEL, lives)

fun inParallel(count: Int, fn: (Int) -> Live<*>) =
  inParallel(List(count) { fn(it) })
