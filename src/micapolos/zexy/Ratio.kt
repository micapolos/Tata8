package micapolos.zexy

class Ratio<T: Value<T>>(val numerator: Value<T>, val denominator: Value<T>)

infix fun Int.by(i: Int) = value.by(i.value)
infix fun Int.by(value: Value<Integer>) = value.by(value)
infix fun <T: Value<T>> Value<T>.by(i: Int) = by(i.value)
infix fun <T: Value<T>> Value<T>.by(value: Value<T>) = Ratio(this, value)