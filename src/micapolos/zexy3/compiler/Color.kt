package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Color
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation
import micapolos.tata8.Color as TataColor

fun Compiler.animatedColor(color: Color): Animated<TataColor> =
  TODO()

fun Compiler.colorEvaluator(color: Color): ObjectEvaluator<TataColor> =
  TODO()

fun Compiler.colorAnimation(color: Color): Animation = noAnimation