package micapolos.zexy3

class Variable<T: Value<T>>(val block: Block<*>, val initial: T): Value<T>

fun <T: Value<T>> Block<*>.newVariable(initial: T): Variable<T> = Variable(this, initial)

fun Block<*>.newVariable2(initial: Double): Variable<Number> = newVariable<Number>(number(initial))