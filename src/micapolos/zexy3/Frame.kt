package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber

object Frame {
  val step = Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))
  val count = variable(0) { it.add(1).everyFrame }
}

val frame = Frame

