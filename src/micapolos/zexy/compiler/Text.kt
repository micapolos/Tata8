package micapolos.zexy.compiler

import micapolos.zexy.indexed.Text
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.animatedTextJoin
import micapolos.zexy.runtime.animatedTextSlice
import micapolos.zexy.runtime.instantAnimation

fun Compiler.animatedText(text: Text): Animated<String> =
  when (text) {
    is Text.Constant -> Animated(ObjectEvaluator { text.string }, ObjectEvaluator { instantAnimation })
    is Text.Slice ->
      animatedTextSlice(
        animated(text.text) as Animated<String>,
        animated(text.start) as Animated<Int>,
        animated(text.length) as Animated<Int>)
    is Text.Join ->
      animatedTextJoin(text.texts.map { animated(it) as Animated<String> })
  }
