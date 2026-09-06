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

fun <T> Live<T>.set(live: Live<T>): Live<Run> =
  Live.Set(variable, live)

fun run(vararg lives: Live<Run>) =
  run(lives.toList())

fun run(lives: List<Live<Run>>): Live<Run> =
  Live.Application(Unit::class, Primitive.PARALLEL, lives)

fun repeat(count: Int, fn: (Int) -> Live<Run>) =
  run(List(count) { fn(it) })
