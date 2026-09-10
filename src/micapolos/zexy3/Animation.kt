package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber

object Animation {
  val step: Value<Number> = Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))
}