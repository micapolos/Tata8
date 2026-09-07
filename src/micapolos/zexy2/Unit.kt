package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.livePause

fun pause(seconds: Double) = pause(seconds.live)
fun pause(seconds: Live<Double>) = livePause(seconds)

fun sequence(vararg lives: Live<Unit>): Live<Unit> =
  Live.Block(lives.toList())

fun repeatWhile(body: Live<Unit>, condition: Boolean): Live<Unit> =
  repeatWhile(body, condition.live)

fun repeatWhile(body: Live<Unit>, condition: Live<Boolean>): Live<Unit> =
  Live.DoWhile(body, condition)

fun repeat(body: Live<Unit>): Live<Unit> =
  repeatWhile(body, true)