package micapolos.zexy3.indexer

import micapolos.lookup
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.model.Variable as ModelVariable

class Indexer {
  internal val variableMap = mutableMapOf<ModelVariable<*>, Variable<*>>()
  val initialValuesOf: (IndexType) -> MutableList<Value<*>> = lookup { mutableListOf() }

  @Suppress("UNCHECKED_CAST")
  fun <T: Value<T>> variableOrNull(modelVariable: ModelVariable<*>): Variable<T>? =
    variableMap[modelVariable] as Variable<T>?
}