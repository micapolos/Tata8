package micapolos.zexy3.indexer

import micapolos.lookup
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.model.Variable as ModelVariable

class Indexer {
  internal val variableMap = mutableMapOf<ModelVariable<*>, Variable<*>>()
  internal val initialValuesOf: (IndexType) -> MutableList<Value<*>> = lookup { mutableListOf() }
  internal val initialValues = mutableListOf<Value<*>>()

  @Suppress("UNCHECKED_CAST")
  fun <T: Value<T>> variableOrNull(modelVariable: ModelVariable<*>): Variable<T>? =
    variableMap[modelVariable] as Variable<T>?

  @Suppress("UNCHECKED_CAST")
  fun <T : Value<T>> initialValues(indexType: IndexType): MutableList<T> =
    initialValuesOf(indexType) as MutableList<T>
}