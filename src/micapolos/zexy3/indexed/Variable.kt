package micapolos.zexy3.indexed

enum class IndexType {
  INTEGER, NUMBER, OTHER
}

class Variable<T : Value<T>>(val indexType: IndexType, val index: Int): Value<T>
