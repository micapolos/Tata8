package micapolos.zexy.indexed

class Variable<T : Value<T>>(val indexType: IndexType, val typedIndex: Int, val index: Int): Value<T>
