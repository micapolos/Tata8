package micapolos

inline fun <T: Any> T?.orIfNull(fn: () -> T): T = this ?: fn()

inline fun <reified E: Enum<E>, V> lookup(crossinline init: (E) -> V): (E) -> V {
  val values = enumValues<E>()
  val array = Array<Any?>(values.size) { init(values[it]) }
  return { e ->
    @Suppress("UNCHECKED_CAST")
    array[e.ordinal] as V
  }
}