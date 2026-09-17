package micapolos.zexy.indexer

import micapolos.util.orIfNull
import micapolos.zexy.indexed.Variable
import micapolos.zexy.model.Variable as ModelVariable

fun Indexer.indexedVariable(model: ModelVariable): Variable =
  variableOrNull(model).orIfNull {
    val indexType = model.indexType
    val indexed = indexed(model.initial)
    val typedInitialValues = initialValuesOf(indexType)
    val typedIndex = typedInitialValues.size
    val index = initialValues.size
    val variable = Variable(indexType, typedIndex, index)
    variableMap[model] = variable
    typedInitialValues.add(indexed)
    initialValues.add(indexed)
    return variable
  }
