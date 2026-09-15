package micapolos.zexy.compiler

import micapolos.zexy.indexed.Void
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.instantAnimation

fun Compiler.animatedVoid(indexed: Void): Animated<*> =
  when (indexed) {
    is Void.Empty ->
      Animated(ObjectEvaluator { Unit }, instantAnimation)
  }
