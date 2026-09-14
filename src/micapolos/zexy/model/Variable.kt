package micapolos.zexy.model

data class Variable<T: Value<T>>(val initial: Value<T>): Value<T>