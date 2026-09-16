package micapolos.zexy

import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Value as ModelValue

class Integer internal constructor(model: Any) : Value<Integer>(model)

internal val Value<Integer>.modelInteger get() = modelOrChildren as ModelValue<ModelInteger>
internal val Value<Integer>.cast get() = modelInteger as ModelInteger

val Int.value: Value<Integer> get() = Integer(ModelInteger.Constant(this))

fun variable(initial: Int) = variable(initial.value)

internal fun Value<Integer>.apply(op1: ModelInteger.Op1): Value<Integer> =
  Integer(ModelInteger.Apply1(op1, modelInteger))

internal fun Value<Integer>.apply(op2: ModelInteger.Op2, integer: Value<Integer>): Value<Integer> =
  Integer(ModelInteger.Apply2(op2, modelInteger, integer.modelInteger))

val Value<Integer>.isNotZero get() = Bool(apply(ModelInteger.Op1.NOT_ZERO).model)
val Value<Integer>.bool get() = isNotZero

operator fun Value<Integer>.unaryMinus() = apply(ModelInteger.Op1.NEG)

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

fun Value<Integer>.isEqualTo(i: Int): Value<Bool> = isEqualTo(i.value)
fun Value<Integer>.isEqualTo(integer: Value<Integer>): Value<Bool> = apply(ModelInteger.Op2.EQ, integer).bool

fun Value<Integer>.compareTo(i: Int) = compareTo(i.value)
fun Value<Integer>.compareTo(integer: Value<Integer>): Value<Integer> = apply(ModelInteger.Op2.CMP, integer)

fun Value<Integer>.isLessThan(i: Int) = isLessThan(i.value)
fun Value<Integer>.isLessThan(integer: Value<Integer>): Value<Bool> = compareTo(integer).isEqualTo(-1)

fun Value<Integer>.isGreaterThan(i: Int) = isGreaterThan(i.value)
fun Value<Integer>.isGreaterThan(integer: Value<Integer>): Value<Bool> = compareTo(integer).isEqualTo(1)

infix fun Value<Integer>.and(i: Int) = and(i.value)
infix fun Value<Integer>.and(integer: Value<Integer>) = apply(ModelInteger.Op2.AND, integer)

infix fun Value<Integer>.or(i: Int) = or(i.value)
infix fun Value<Integer>.or(integer: Value<Integer>) = apply(ModelInteger.Op2.OR, integer)

infix fun Value<Integer>.xor(i: Int) = xor(i.value)
infix fun Value<Integer>.xor(integer: Value<Integer>) = apply(ModelInteger.Op2.XOR, integer)

@JvmName("IntegerSelect")
fun <T : Value<T>> Value<Integer>.selectFrom(values: List<Value<T>>): Value<T> =
  when (values.first().modelOrChildren) {
    is ModelValue<*> -> Value(ModelValue.Select(model as ModelValue<ModelInteger>, values.map { it.model }))
    else -> Value(children.map { selectFrom(it.children) })
  }

fun <T : Value<T>> Value<Integer>.selectFrom(value: Value<T>, vararg values: Value<T>): Value<T> =
  selectFrom(listOf(value, *values))

operator fun <T : Value<T>> List<Value<T>>.get(index: Value<Integer>): Value<T> =
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

fun Value<Integer>.min(integer: Int) = min(integer.value)
fun Value<Integer>.min(integer: Value<Integer>) = isLessThan(integer).ifTrue(integer).orElse(this)

fun Value<Integer>.max(integer: Int) = max(integer.value)
fun Value<Integer>.max(integer: Value<Integer>) = isGreaterThan(integer).ifTrue(integer).orElse(this)

context(_: Action.Block)
infix fun Value<Integer>.set(i: Int) {
  set(i.value)
}

context(_: Action.Block)
infix fun Value<Integer>.bind(i: Int) {
  bind(i.value)
}

context(_: Action.Block)
infix fun Value<Integer>.add(i: Int) {
  add(i.value)
}

context(_: Action.Block)
infix fun Value<Integer>.add(value: Value<Integer>) {
  set(this + value)
}

context(_: Animation.Block)
infix fun Value<Integer>.set(i: Int) {
  set(i.value)
}

context(_: Animation.Block)
infix fun Value<Integer>.bind(i: Int) {
  bind(i.value)
}

context(_: Animation.Block)
infix fun Value<Integer>.add(i: Int) {
  add(i.value)
}

context(_: Animation.Block)
infix fun Value<Integer>.add(value: Value<Integer>) {
  set(this + value)
}

context(_: Animation.Block)
infix fun Value<Integer>.subtract(i: Int) {
  subtract(i.value)
}

context(_: Animation.Block)
infix fun Value<Integer>.subtract(value: Value<Integer>) {
  set(this - value)
}

context(_: Animation.Block)
val Value<Integer>.increment get() = add(1)

context(_: Animation.Block)
val Value<Integer>.decrement get() = subtract(1)

context(animationBlock: Animation.Block)
val Value<Integer>.change: Value<Event>
  get() {
    val previous = variable(this)
    val current = variable(this)

    animationBlock.everyStep {
      previous set current
      current set this@change
    }

    return previous.isEqualTo(current).not().occurrence
  }

context(animationBlock: Animation.Block)
fun Value<Integer>.changeTo(integer: Value<Integer>): Value<Event> =
  change.and(isEqualTo(integer))

context(animationBlock: Animation.Block)
fun Value<Integer>.elastic(ratio: Ratio<Integer>): Value<Integer> = run {
  val current = variable(this)

  animationBlock.everyStep {
    current set (this@elastic - (this@elastic - current) * (ratio.numerator - 1) / ratio.denominator)
  }

  return current
}

context(animationBlock: Animation.Block)
val Value<Integer>.elastic: Value<Integer> get() = elastic(3 by 4)


context(animationBlock: Animation.Block)
fun repeat(integer: Value<Integer>, fn: Animation.Block.() -> Unit) {
  with(animationBlock) {
    val counter = variable(integer)
    repeatWhile(!counter.isEqualTo(0)) {
      fn()
      counter subtract 1
    }
  }
}
