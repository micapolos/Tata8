package micapolos.zexy.runtime

import java.awt.SystemColor.text

fun textLengthEvaluator(textEvaluator: Evaluator<String>): Evaluator<Int> =
  IntEvaluator { textEvaluator.evalObject().length }

fun textSliceEvaluator(
  textEvaluator: Evaluator<String>,
  startEvaluator: Evaluator<Int>,
  lengthEvaluator: Evaluator<Int>,
): ObjectEvaluator<String> =
  ObjectEvaluator {
    val start = startEvaluator.evalInt()
    textEvaluator.evalObject().substring(start, start + lengthEvaluator.evalInt())
  }

fun textJoinEvaluator(textEvaluators: Array<Evaluator<String>>): ObjectEvaluator<String> =
  ObjectEvaluator {
    textEvaluators.joinToString(separator = "") { it.evalObject() }
  }
