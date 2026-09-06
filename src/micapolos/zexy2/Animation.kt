package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

class Animation<out T>

fun inParallel(live: Live<*>, vararg lives: Live<*>) =
  inParallel(listOf(live, *lives))

fun inParallel(lives: List<Live<*>>) =
  Live.Application<Animation<*>>(Animation::class, Primitive.PARALLEL, lives)
