package micapolos.zexy3.compiler

import micapolos.zexy.Drawable
import micapolos.zexy3.runtime.ObjectEvaluator

class Game(
  val title: String,
  val width: Int,
  val height: Int,
  val drawableEvaluator: ObjectEvaluator<Drawable>,
)