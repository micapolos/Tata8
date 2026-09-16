package micapolos.zexy

import micapolos.zexy.model.Number as ModelNumber

object Frame

val frame = Frame

context(animationBlock: Animation.Block)
val Frame.count: Value<Integer> get() {
  val counter = variable(0)
  animationBlock.everyStep {
    counter add 1
  }
  return counter
}

context(_: Animation.Block)
val Frame.step: Value<Number> get() =
  Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))