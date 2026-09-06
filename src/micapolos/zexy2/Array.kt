package micapolos.zexy2

import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

operator fun <T> Array<T>.get(index: Live<Int>): Live<T> =
  Live.Application(first()!!::class, Primitive.ARRAY_GET, listOf(live(Array::class), index))
