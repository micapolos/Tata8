package micapolos.zexy2

import micapolos.zexy2.live.Live
import kotlin.reflect.KClass

val Boolean.live get() = live(Boolean::class)

fun newVariable(b: Boolean): Live<Boolean> = newVariable(b.live)

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

