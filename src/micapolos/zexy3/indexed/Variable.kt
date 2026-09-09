package micapolos.zexy3.indexed

class Variable<T : Value<T>>(val indexType: IndexType, val index: Int): Value<T>
