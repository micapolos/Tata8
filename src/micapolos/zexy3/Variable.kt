package micapolos.zexy3

import micapolos.zexy3.model.Variable as ModelVariable
import micapolos.zexy3.model.Void as ModelVoid

open class Variable<T : Value<T>> internal constructor(modelVariable: ModelVariable<*>): Value<T>(modelVariable)

internal val Variable<*>.erasedModelVariable get() = model as ModelVariable<ModelVoid>

fun <T : Value<T>> animatedVariable(initial: Value<T>): Variable<T> = Variable(ModelVariable(initial.model))

fun <T : Value<T>> animatedVariable(initial: Value<T>, fn: (Value<T>) -> Value<Action>): Value<T> =
  animatedVariable(initial).also(fn)