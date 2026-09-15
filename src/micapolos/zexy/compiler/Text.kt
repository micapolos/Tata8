package micapolos.zexy.compiler

import micapolos.zexy.indexed.Text
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.textJoinEvaluator
import micapolos.zexy.runtime.textSliceEvaluator

fun Compiler.textEvaluator(text: Text): Evaluator<String> =
  when (text) {
    is Text.Constant ->
      ObjectEvaluator { text.string }
    is Text.Slice ->
      textSliceEvaluator(objectEvaluator(text.text), intEvaluator(text.start), intEvaluator(text.length))
    is Text.Join ->
      textJoinEvaluator(text.texts.map { evaluator(it) as Evaluator<String> }.toTypedArray())
  }
