package micapolos.zexy.indexer

import micapolos.util.lookup
import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Variable
import micapolos.zexy.model.Variable as ModelVariable

class Indexer {
  internal val variableMap = mutableMapOf<ModelVariable, Variable>()
  internal val initialValuesOf: (IndexType) -> MutableList<Value> = lookup { mutableListOf() }
  internal val initialValues = mutableListOf<Value>()

  @Suppress("UNCHECKED_CAST")
  fun variableOrNull(modelVariable: ModelVariable): Variable? =
    variableMap[modelVariable] as Variable?

  @Suppress("UNCHECKED_CAST")
  fun initialValues(indexType: IndexType): MutableList<Value> =
    initialValuesOf(indexType)
}