package micapolos.zexy.runtime

import micapolos.tata8.Color
import micapolos.tata8.Game as TataGame
import micapolos.tata8.Shader

fun Game.show() {
  TataGame.title = title
  val animation = this.animationEvaluator
  val evaluator = drawingEvaluator
  animation.evalObject().start()
  evaluator.evalBoxed().drawOn(TataGame.background.canvas)
  val stepSeconds = TataGame.FRAME_SECONDS.toDouble()
  var gameSeconds = 0.0
  TataGame.background.tileMap.isEnabled = false;
  TataGame.foreground.isEnabled = false;
  TataGame.spritesAreEnabled = false;
  TataGame.onUpdate = {
    if (TataGame.keys.reset.pressed()) {
      animation.evalObject().start()
      gameSeconds = 0.0
    }
    TataGame.background.canvas.clear()
    val leftOverSeconds = animation.evalObject().step(stepSeconds)
    val isFinished = leftOverSeconds != 0.0
    evaluator.evalBoxed().drawOn(TataGame.background.canvas)
    gameSeconds += stepSeconds
    if (isFinished) {
      gameSeconds -= leftOverSeconds
      val string = "Finished in $gameSeconds seconds."
      val width = TataGame.font.width(string)
      TataGame.background.canvas.draw(string, (TataGame.WIDTH - width) / 2, 8, Color.YELLOW, TataGame.font, true)
    }
  }
  TataGame.screen.shader = Shader.CRT_PHOSPHOR
  TataGame.start()
}

fun Drawing.show() {
  ObjectEvaluator { this }.show()
}

fun Evaluator<Drawing>.show() {
  Game(drawingEvaluator = this).show()
}

fun Animated<Drawing>.show() {
  Game(drawingEvaluator = evaluator, animationEvaluator = animation).show()
}
