package micapolos.zexy2.runtime

fun interface Value<T> {
  operator fun invoke(): T
}