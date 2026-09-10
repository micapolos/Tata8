package micapolos.zexy3

import micapolos.zexy3.model.Variable as ModelVariable
import micapolos.zexy3.model.Void as ModelVoid

open class Variable<T : Value<T>> internal constructor(modelVariable: ModelVariable<*>): Value<T>(modelVariable)

internal val Variable<*>.erasedModelVariable get() = model as ModelVariable<ModelVoid>

fun <T : Value<T>> newVariable(initial: Value<T>): Variable<T> = Variable(ModelVariable(initial.model))

fun <T: Value<T>> Variable<T>.set(value: Value<T>): Void =
  Void(ModelVoid.Set(erasedModelVariable, value.model))
