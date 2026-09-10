package micapolos.zexy3

import micapolos.zexy3.model.Number as ModelNumber

class Animation internal constructor(impl: Any): Value<Animation>(impl)

val Animation.step get() = children[0] as Value<Number>

val animation = Animation(listOf(Number(ModelNumber.Apply0(ModelNumber.Op0.FRAME_TIME))))
