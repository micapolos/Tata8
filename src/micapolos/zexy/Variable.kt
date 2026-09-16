package micapolos.zexy

import micapolos.zexy.model.Variable as ModelVariable

open class Variable<T : Value<T>> internal constructor(modelVariable: ModelVariable<*>): ValueWithModel<T>(modelVariable)

fun <T : Value<T>> variable(initial: Value<T>): Variable<T> = Variable(ModelVariable(initial.model))
