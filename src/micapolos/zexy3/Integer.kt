package micapolos.zexy3

import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Value as ModelValue

class Integer internal constructor(model: Any): Value<Integer>(model)

internal val Value<Integer>.modelInteger get() = modelOrChildren as ModelValue<ModelInteger>
internal val Value<Integer>.cast get() = modelInteger as ModelInteger

val Int.value: Value<Integer> get() = Integer(ModelInteger.Constant(this))

fun variable(initial: Int) = variable(initial.value)
fun variable(initial: Int, fn: (Value<Integer>) -> Value<Activity>) = variable(initial.value, fn)

internal fun Value<Integer>.apply(op2: ModelInteger.Op2, integer: Value<Integer>): Value<Integer> =
  Integer(ModelInteger.Apply2(op2, modelInteger, integer.modelInteger))

operator fun Value<Integer>.unaryMinus() = 0.value - this

operator fun Value<Integer>.plus(i: Int) = plus(i.value)
operator fun Value<Integer>.plus(integer: Value<Integer>) = apply(ModelInteger.Op2.ADD, integer)

operator fun Value<Integer>.minus(i: Int) = minus(i.value)
operator fun Value<Integer>.minus(integer: Value<Integer>) = apply(ModelInteger.Op2.SUB, integer)

operator fun Value<Integer>.times(i: Int) = times(i.value)
operator fun Value<Integer>.times(integer: Value<Integer>) = apply(ModelInteger.Op2.MUL, integer)

operator fun Value<Integer>.div(i: Int) = div(i.value)
operator fun Value<Integer>.div(integer: Value<Integer>) = apply(ModelInteger.Op2.DIV, integer)

operator fun Value<Integer>.rem(i: Int) = rem(i.value)
operator fun Value<Integer>.rem(integer: Value<Integer>) = apply(ModelInteger.Op2.REM, integer)

fun Value<Integer>.equals(i: Int) = equals(i.value)
fun Value<Integer>.equals(integer: Value<Integer>) = Bool(apply(ModelInteger.Op2.EQ, integer))

fun Value<Integer>.isLessThan(i: Int) = isLessThan(i.value)
fun Value<Integer>.isLessThan(integer: Value<Integer>) = Bool(apply(ModelInteger.Op2.LT, integer))

infix fun Value<Integer>.and(i: Int) = times(i.value)
infix fun Value<Integer>.and(integer: Value<Integer>) = apply(ModelInteger.Op2.AND, integer)

infix fun Value<Integer>.or(i: Int) = times(i.value)
infix fun Value<Integer>.or(integer: Value<Integer>) = apply(ModelInteger.Op2.OR, integer)

infix fun Value<Integer>.xor(i: Int) = times(i.value)
infix fun Value<Integer>.xor(integer: Value<Integer>) = apply(ModelInteger.Op2.XOR, integer)

@JvmName("IntegerSelect")
fun <T: Value<T>> Value<Integer>.selectFrom(values: List<Value<T>>): Value<T> =
  when (values.first().modelOrChildren) {
    is ModelValue<*> -> Value(ModelValue.Select(cast, values.map { it.model }))
    else -> Value(children.map { selectFrom(it.children) })
  }

fun <T: Value<T>> Value<Integer>.selectFrom(value: Value<T>, vararg values: Value<T>): Value<T> =
  selectFrom(listOf(value, *values))

operator fun <T: Value<T>> List<T>.get(index: Value<Integer>): Value<T> =
  index.selectFrom(this)

fun Value<Bool>.selectTrueFalse(trueCase: Int, falseCase: Int): Value<Integer> =
  selectTrueFalse(trueCase.value, falseCase.value)

fun Value<Bool>.ifTrue(trueCase: Int) = ifTrue(trueCase.value)
fun IfTrue<Integer>.orElse(falseCase: Int) = orElse(falseCase.value)

@JvmName("IntegerSelectInt")
fun Value<Integer>.selectFrom(cases: List<Int>): Value<Integer> =
  selectFrom(cases.map { it.value })

@JvmName("IntegerSelectInt")
fun Value<Integer>.selectFrom(firstCast: Int, vararg otherCases: Int): Value<Integer> =
  selectFrom(listOf(firstCast, *otherCases.toTypedArray()))

val Value<Number>.integer get() = Integer(ModelInteger.FromNumber(modelNumber))

fun Value<Integer>.set(i: Int) = set(i.value)
fun Value<Integer>.capture(i: Int) = capture(i.value)
fun Value<Integer>.add(i: Int) = add(i.value)
fun Value<Integer>.add(i: Value<Integer>) = capture(this + i)
fun Value<Integer>.subtract(i: Int) = capture(this - 1)
fun Value<Integer>.multiply(i: Int) = capture(this * 1)

val Value<Integer>.changed: Event get() = TODO()