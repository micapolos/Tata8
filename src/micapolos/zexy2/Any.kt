package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import micapolos.zexy2.live.Run
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

object Block: Run

fun block(vararg lives: Live<Run>) =
  block(lives.toList())

fun block(lives: List<Live<Run>>): Live<Block> =
  Live.Application(Run::class, Primitive.PARALLEL, lives)

fun repeat(count: Int, fn: (Int) -> Live<Run>) =
  block(List(count) { fn(it) })
