package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Text
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation

fun Compiler.animatedText(text: Text): Animated<String> =
  Animated(textEvaluator(text), textAnimation(text))

fun Compiler.textAnimation(text: Text): Animation =
  when (text) {
    is Text.Constant -> noAnimation
  }

fun Compiler.textEvaluator(text: Text): ObjectEvaluator<String> =
  when (text) {
    is Text.Constant -> ObjectEvaluator { text.string }
  }