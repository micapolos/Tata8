package micapolos.zexy

import micapolos.zexy.model.Number as ModelNumber

object Frame

val frame = Frame

context(animationBuilder: Animation.Builder)
val Frame.count2: Value<Integer> get() {
  val counter = variable(0)
  animationBuilder.everyFrame {
    counter.logAs("incrementing counter")
    counter add2 1
    counter.logAs("incremented counter")
  }
  return counter
}

context(_: Animation.Builder)
val Frame.step2: Value<Number> get() =
  Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))