package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import kotlin.reflect.KClass

val Boolean.live get() = live(Boolean::class)

fun liveVariable(b: Boolean): Live<Boolean> = liveVariable(b.live)

fun Live<Boolean>.set(d: Boolean): Live<Unit> =
  set(d.live)

operator fun Live<Boolean>.not(): Live<Boolean> =
  Live.Application(Boolean::class, Primitive.BOOLEAN_NOT, listOf(this))

infix fun Live<Boolean>.and(b: Boolean): Live<Boolean> = and(b.live)
infix fun Live<Boolean>.and(b: Live<Boolean>): Live<Boolean> =
  Live.Application(Boolean::class, Primitive.BOOLEAN_AND, listOf(this, b))

infix fun Live<Boolean>.or(b: Boolean): Live<Boolean> = or(b.live)
infix fun Live<Boolean>.or(b: Live<Boolean>): Live<Boolean> =
  Live.Application(Boolean::class, Primitive.BOOLEAN_OR, listOf(this, b))

fun Live<Boolean>.ifTrue(b: Boolean) = ifTrue(b.live)
fun Live<Boolean>.ifTrue(i: Int) = ifTrue(i.live)
fun Live<Boolean>.ifTrue(d: Double) = ifTrue(d.live)
fun <T> Live<Boolean>.ifTrue(kClass: KClass<*>, t: T) = ifTrue(t.live(kClass))
fun <T> Live<Boolean>.ifTrue(trueLive: Live<T>) = IfTrue(this, trueLive)
data class IfTrue<T>(val condition: Live<Boolean>, val trueLive: Live<T>)

fun IfTrue<Boolean>.orElse(b: Boolean) = orElse(b.live)
fun IfTrue<Int>.orElse(i: Int) = orElse(i.live)
fun IfTrue<Double>.orElse(d: Double) = orElse(d.live)
fun <T> IfTrue<T>.orElse(t: T) = orElse(t.live(trueLive.kClass))
fun <T> IfTrue<T>.orElse(falseLive: Live<T>) =
  Live.Conditional(trueLive.kClass, condition, trueLive, falseLive)

