package micapolos.zexy.compiler

import micapolos.zexy.indexed.Game
import micapolos.zexy.runtime.Animation
import micapolos.zexy.runtime.Drawing
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame {
  val drawingEvaluator = objectEvaluator<Drawing>(game.drawing)
  val animationEvaluator = evaluator(game.animation) as Evaluator<Animation>
  val initialEvaluators = state.evaluatorArray.clone()
  val evaluatorArray = state.evaluatorArray
  return RuntimeGame(
    game.title,
    drawingEvaluator,
    animationEvaluator)
}