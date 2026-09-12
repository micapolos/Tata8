package micapolos.zexy

import micapolos.zexy.model.Number as ModelNumber

object Frame {
  val step = Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))
  val count = variable(0) { it.set(0).then(it.add(1).everyFrame) }
}

val frame = Frame

