package micapolos.zexy.compiler

import micapolos.zexy.indexed.Text
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.instantAnimation

fun Compiler.animatedText(text: Text): Animated<String> =
  when (text) {
    is Text.Constant -> Animated(ObjectEvaluator { text.string }, instantAnimation)
  }
