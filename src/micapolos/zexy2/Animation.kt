package micapolos.zexy2

import micapolos.zexy2.ast.Live

class Animation<out T>

fun inParallel(live: Live<Animation<*>>, vararg lives: Live<Animation<*>>) =
  inParallel(listOf(live, *lives))

fun inParallel(lives: List<Live<Animation<*>>>) =
  Live.Application<Animation<*>>(Animation::class, "parallel", lives)

fun inSequence(live: Live<Animation<*>>, vararg lives: Live<Animation<*>>) =
  inSequence(listOf(live, *lives))

fun inSequence(lives: List<Live<Animation<*>>>) =
  Live.Application<Animation<*>>(Animation::class, "sequence", lives)
