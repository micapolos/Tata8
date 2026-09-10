package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Value as ModelValue

class Integer internal constructor(model: ModelInteger): Value<Integer>(model)

internal val Value<Integer>.modelInteger get() = modelOrChildren as ModelValue<ModelInteger>
internal val Value<Integer>.cast get() = modelInteger as ModelInteger

val Int.value get() = Integer(ModelInteger.Constant(this))

fun newVariable(i: Int) = newVariable(i.value)

internal fun Value<Integer>.apply(op2: ModelInteger.Op2, integer: Value<Integer>): Value<Integer> =
  Integer(ModelInteger.Apply2(op2, modelInteger, integer.modelInteger))

operator fun Value<Integer>.plus(i: Int) = plus(i.value)
operator fun Value<Integer>.plus(integer: Value<Integer>) = apply(ModelInteger.Op2.ADD, integer)

operator fun Value<Integer>.minus(i: Int) = minus(i.value)
operator fun Value<Integer>.minus(integer: Value<Integer>) = apply(ModelInteger.Op2.SUB, integer)

operator fun Value<Integer>.times(i: Int) = times(i.value)
operator fun Value<Integer>.times(integer: Value<Integer>) = apply(ModelInteger.Op2.MUL, integer)

infix fun Value<Integer>.and(i: Int) = times(i.value)
infix fun Value<Integer>.and(integer: Value<Integer>) = apply(ModelInteger.Op2.AND, integer)

infix fun Value<Integer>.or(i: Int) = times(i.value)
infix fun Value<Integer>.or(integer: Value<Integer>) = apply(ModelInteger.Op2.OR, integer)

infix fun Value<Integer>.xor(i: Int) = times(i.value)
infix fun Value<Integer>.xor(integer: Value<Integer>) = apply(ModelInteger.Op2.XOR, integer)

fun <T: Value<T>> Value<Integer>.select(values: List<Value<T>>): Value<T> =
  when (values.first()) {
    is ModelValue<*> -> Value(ModelValue.Select(cast, values.map { it.model }))
    else -> Value(children.map { this@select.select(it.children) })
  }

fun <T: Value<T>> Value<Integer>.select(vararg values: Value<T>): Value<T> =
  select(values.toList())

val Value<Number>.integer get() = Integer(ModelInteger.FromNumber(modelNumber))