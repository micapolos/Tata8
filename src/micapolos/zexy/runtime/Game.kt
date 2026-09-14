package micapolos.zexy.runtime

import micapolos.tata8.Color
import micapolos.tata8.Shader
import kotlin.math.min
import micapolos.tata8.Game as TataGame

class Game(
  val title: String = "Zexy game",
  val animatedDrawing: Animated<Drawing> = Animated(ObjectEvaluator { Drawing { } }, instantAnimation),
  val animation: Animation = instantAnimation,
)

fun Game.show() {
  TataGame.title = title
  val animation = animatedDrawing.animation
  animation.start()
  this.animation.start()
  val evaluator = animatedDrawing.evaluator
  evaluator.evalBoxed().drawOn(TataGame.background.canvas)
  val stepSeconds = TataGame.FRAME_SECONDS.toDouble()
  var gameSeconds = 0.0
  TataGame.background.tileMap.isEnabled = false;
  TataGame.foreground.isEnabled = false;
  TataGame.spritesAreEnabled = false;
  TataGame.onUpdate = {
    if (TataGame.keys.reset.pressed()) {
      animation.start()
      this.animation.start()
      gameSeconds = 0.0
    }
    TataGame.background.canvas.clear()
    val leftOverSeconds = min(
      animation.step(stepSeconds),
      animation.step(stepSeconds)
    )
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
