package micapolos.zexy.compiler

import micapolos.zexy.indexed.Game
import micapolos.zexy.runtime.*
import micapolos.zexy.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame {
  val drawingEvaluator = objectEvaluator<Drawing>(game.drawing)
  val animationEvaluator = objectEvaluator<Animation>(game.animation)
  val initialEvaluators = state.evaluatorArray.clone()
  val evaluatorArray = state.evaluatorArray
  val resetAction = Action {
    for (i in initialEvaluators.indices) {
      evaluatorArray[i] = initialEvaluators[i]
    }
  }
  val resetActionEvaluator = ObjectEvaluator { resetAction }
  val resetAnimation = instantAnimation(resetActionEvaluator)
  val resetAnimationEvaluator = ObjectEvaluator { resetAnimation }
  return RuntimeGame(
    game.title,
    drawingEvaluator,
    ObjectEvaluator {
      parallel(listOf(resetAnimationEvaluator, animationEvaluator))
    })
}