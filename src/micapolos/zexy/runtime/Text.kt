package micapolos.zexy.runtime

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

fun animatedTextLength(animatedText: Animated<String>) =
  Animated(
    textLengthEvaluator(animatedText.evaluator),
    animatedText.animation
  )

fun animatedTextSlice(
  animatedText: Animated<String>,
  animatedStart: Animated<Int>,
  animatedEnd: Animated<Int>,
) =
  Animated(
    textSliceEvaluator(animatedText.evaluator, animatedStart.evaluator, animatedEnd.evaluator),
    parallel(animatedText.animation, animatedStart.animation, animatedEnd.animation)
  )

fun animatedTextJoin(animatedTexts: List<Animated<String>>) =
  Animated(
    textJoinEvaluator(animatedTexts.map { it.evaluator }.toTypedArray()),
    parallel(animatedTexts.map { it.animation })
  )