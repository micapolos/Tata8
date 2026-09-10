package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger

object Mouse {
  val x: Value<Integer> = Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_X))
  val y: Value<Integer> = Integer(ModelInteger.Apply0(ModelInteger.Op0.MOUSE_Y))
}

