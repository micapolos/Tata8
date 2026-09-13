package micapolos.zexy.runtime

class State(
  val intArray: IntArray = IntArray(0),
  val doubleArray: DoubleArray = DoubleArray(0),
  val objectArray: Array<Any?> = arrayOfNulls(0),
  val animatedArray: Array<Animated<*>?> = arrayOfNulls(0),
)
