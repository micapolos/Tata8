package micapolos.zexy3.runtime

class Box<T>(var supplier: (() -> T)? = null, var value: T) {
  fun get() = if (supplier != null) supplier!!() else value

  fun set(v: T) {
    supplier = null
    value = v
  }
}