package micapolos.zexy3.runtime

class Box<T>(var defaultValue: T, var supplier: (() -> T)? = null) {
  var value
    get() = if (supplier != null) supplier!!() else defaultValue
    set(v: T) {
      supplier = null
      value = v
    }
}