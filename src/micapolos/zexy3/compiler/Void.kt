package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Void
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.noAnimation
import micapolos.zexy3.runtime.pauseAnimation

fun Compiler.animation(indexed: Void): Animation =
  when (indexed) {
    Void.Empty -> noAnimation
    Void.Pause -> pauseAnimation { 1.0 }
    is Void.Set<*> -> noAnimation
  }