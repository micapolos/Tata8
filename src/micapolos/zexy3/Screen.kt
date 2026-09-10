package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger

class Screen internal constructor(impl: Any) : Value<Screen>(impl)

val Value<Screen>.size: Value<Size> get() = children[0] as Value<Size>

val screen =
  Screen(
    listOf(
      size(
        Integer(ModelInteger.Apply0(ModelInteger.Op0.SCREEN_WIDTH)),
        Integer(ModelInteger.Apply0(ModelInteger.Op0.SCREEN_HEIGHT))
      )
    )
  )
