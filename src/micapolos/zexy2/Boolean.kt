package micapolos.zexy2

import micapolos.zexy2.ast.Live
import kotlin.reflect.KClass

fun Live<Boolean>.ifTrue(b: Boolean) = ifTrue(constant(b))
fun Live<Boolean>.ifTrue(i: Int) = ifTrue(constant(i))
fun Live<Boolean>.ifTrue(d: Double) = ifTrue(constant(d))
fun <T> Live<Boolean>.ifTrue(kClass: KClass<*>, t: T) = ifTrue(constant(kClass, t))
fun <T> Live<Boolean>.ifTrue(trueLive: Live<T>) = IfTrue(this, trueLive)
data class IfTrue<T>(val condition: Live<Boolean>, val trueLive: Live<T>)

fun IfTrue<Boolean>.orElse(b: Boolean) = orElse(constant(b))
fun IfTrue<Int>.orElse(i: Int) = orElse(constant(i))
fun IfTrue<Double>.orElse(d: Double) = orElse(constant(d))
fun <T> IfTrue<T>.orElse(t: T) = orElse(constant(trueLive.kClass, t))
fun <T> IfTrue<T>.orElse(falseLive: Live<T>) =
  Live.Conditional(trueLive.kClass, condition, trueLive, falseLive)

fun constant(b: Boolean): Live<Boolean> =
  Live.Constant(Boolean::class, b)

fun variable(b: Boolean): Live<Boolean> = variable(constant(b))

