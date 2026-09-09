package micapolos.zexy3.indexer

import micapolos.orIfNull
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.model.ModelInteger
import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Variable as ModelVariable

val ModelVariable<*>.indexType get() = when (initial) {
  is ModelInteger -> IndexType.INTEGER
  is ModelNumber -> IndexType.NUMBER
  else -> IndexType.OTHER
}

fun <T: Value<T>> Indexer.indexed(modelVariable: ModelVariable<*>): Variable<T> =
  variableOrNull<T>(modelVariable).orIfNull {
    val indexType = modelVariable.indexType
    val indexed = indexed(modelVariable.initial)
    val index = initialValuesOf(indexType).size
    val variable = Variable<T>(indexType, index)
    variableMap[modelVariable] = variable
    initialValuesOf(indexType).add(indexed)
    return variable
  }
