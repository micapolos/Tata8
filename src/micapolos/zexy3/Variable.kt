package micapolos.zexy3

import micapolos.zexy3.model.Variable as ModelVariable

open class Variable<T : Value<T>> internal constructor(modelVariable: ModelVariable<*>): Value<T>(modelVariable)

fun <T : Value<T>> variable(initial: Value<T>): Variable<T> = Variable(ModelVariable(initial.model))

fun <T : Value<T>> variable(initial: Value<T>, fn: (Value<T>) -> Value<Activity>): Value<T> =
  variable(initial).also(fn)