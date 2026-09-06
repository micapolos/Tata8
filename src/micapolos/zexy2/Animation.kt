package micapolos.zexy2

import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive

class Animation<out T>

fun inParallel(live: Live<Animation<*>>, vararg lives: Live<Animation<*>>) =
  inParallel(listOf(live, *lives))

fun inParallel(lives: List<Live<Animation<*>>>) =
  Live.Application<Animation<*>>(Animation::class, Primitive.PARALLEL, lives)
