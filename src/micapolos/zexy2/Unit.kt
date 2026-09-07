package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.livePause

fun pause(seconds: Double) = pause(seconds.live)
fun pause(seconds: Live<Double>) = livePause(seconds)

fun sequence(vararg lives: Live<Unit>): Live<Unit> =
  Live.Block(lives.toList())

fun Live<Unit>.then(live: Live<Unit>): Live<Unit> =
  sequence(this, live)

fun Live<Unit>.repeatWhile(condition: Boolean): Live<Unit> =
  repeatWhile(condition.live)

fun Live<Unit>.repeatWhile(condition: Live<Boolean>): Live<Unit> =
  Live.DoWhile(this, condition)

val Live<Unit>.repeat: Live<Unit> get() =
  repeatWhile(true)

fun Live<Unit>.onlyIf(condition: Live<Boolean>): Live<Unit> =
  Live.ConditionalStep(condition, this)

fun Live<Unit>.on(condition: Live<Boolean>): Live<Unit> =
  Live.ConditionalInit(condition, this)
