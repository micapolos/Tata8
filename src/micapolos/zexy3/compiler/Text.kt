package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Text
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.instantAnimation

fun Compiler.animatedText(text: Text): Animated<String> =
  when (text) {
    is Text.Constant -> Animated(ObjectEvaluator { text.string }, instantAnimation)
  }

fun Compiler.textAnimation(text: Text): Animation = TODO()

fun Compiler.textEvaluator(text: Text): ObjectEvaluator<String> = TODO()