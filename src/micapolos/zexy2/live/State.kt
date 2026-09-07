package micapolos.zexy2.live

@Suppress("UNCHECKED_CAST")
data class State<T>(var internalValue: Any? = null) {
  var value: T
    get() = internalValue as T
    set(value) { internalValue = value }
}