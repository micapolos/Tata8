package micapolos.zexy3.indexer

import micapolos.orIfNull
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.model.Integer
import micapolos.zexy3.model.Number as ModelNumber
import micapolos.zexy3.model.Variable as ModelVariable

val ModelVariable<*>.indexType get() = when (initial) {
  is Integer -> IndexType.INTEGER
  is ModelNumber -> IndexType.NUMBER
  else -> IndexType.OTHER
}

fun <T: Value<T>> Indexer.indexed(model: ModelVariable<*>): Variable<T> =
  variableOrNull<T>(model).orIfNull {
    val indexType = model.indexType
    val indexed = indexed(model.initial)
    val initialValues = initialValuesOf(indexType)
    val index = initialValues.size
    val variable = Variable<T>(indexType, index)
    variableMap[model] = variable
    initialValues.add(indexed)
    return variable
  }
