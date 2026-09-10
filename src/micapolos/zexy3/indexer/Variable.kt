package micapolos.zexy3.indexer

import micapolos.orIfNull
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.model.Variable as ModelVariable

fun <T: Value<T>> Indexer.indexedVariable(model: ModelVariable<*>): Variable<T> =
  variableOrNull<T>(model).orIfNull {
    val indexType = model.indexType
    val indexed = indexed(model.initial)
    val typedInitialValues = initialValuesOf(indexType)
    val typedIndex = typedInitialValues.size
    val index = initialValues.size
    val variable = Variable<T>(indexType, typedIndex, index)
    variableMap[model] = variable
    typedInitialValues.add(indexed)
    initialValues.add(indexed)
    return variable
  }
