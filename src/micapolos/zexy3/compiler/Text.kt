package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Text
import micapolos.zexy3.runtime.ObjectEvaluator

fun Compiler.compile(text: Text): ObjectEvaluator<String> =
  when (text) {
    is Text.Constant -> ObjectEvaluator { text.string }
  }