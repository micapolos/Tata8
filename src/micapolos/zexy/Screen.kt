package micapolos.zexy

import micapolos.zexy.model.Integer as ModelInteger

class Screen internal constructor(size: Value<Size>) : ValueWithChildren<Screen>(size)

val Value<Screen>.size: Value<Size> get() = children[0] as Value<Size>

val screen =
  Screen(
      size(
        Integer(ModelInteger.Apply0(ModelInteger.Op0.SCREEN_WIDTH)),
        Integer(ModelInteger.Apply0(ModelInteger.Op0.SCREEN_HEIGHT))
      )
  )
