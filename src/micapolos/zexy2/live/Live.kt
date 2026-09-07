package micapolos.zexy2.live

import kotlin.reflect.KClass

sealed class Live<out T> {
  abstract val kClass: KClass<*>

  object Bottom : Live<Nothing>() {
    override val kClass = Nothing::class
  }

  class Constant<out T>(
    override val kClass: KClass<*>,
    val value: T
  ) : Live<T>()

  class Variable<out T>(
    val initializer: Live<T>
  ) : Live<T>() {
    override val kClass: KClass<*> get() = initializer.kClass
  }

  class Set<out T>(
    val lhs: Live<T>,
    val rhs: Live<T>
  ) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class Init<out T>(
    val lhs: Live<T>,
    val rhs: Live<T>
  ) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class Elastic(val target: Live<Double>) : Live<Double>() {
    override val kClass: KClass<*> get() = Double::class
  }

  class Application<out T>(
    override val kClass: KClass<*>,
    val primitive: Primitive,
    val args: List<Live<*>>
  ) : Live<T>()

  class Conditional<out T>(
    override val kClass: KClass<*>,
    val condition: Live<Boolean>,
    val trueLive: Live<T>,
    val falseLive: Live<T>,
  ) : Live<T>()

  class Pause(val seconds: Live<Double>) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class Block(val lives: List<Live<*>>) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class DoWhile(val body: Live<Unit>, val condition: Live<Boolean>) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class ConditionalStep(val condition: Live<Boolean>, val body: Live<Unit>) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  class ConditionalInit(val condition: Live<Boolean>, val body: Live<Unit>) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }
}

val <T> Live<T>.asApplication get() = this as Live.Application<T>

val liveBottom: Live<Nothing> = Live.Bottom

fun <T : Any> liveConstant(t: T): Live<T> = Live.Constant(t::class, t)

fun <T> liveVariable(initializer: Live<T>): Live<T> = Live.Variable(initializer)

fun <T> liveSet(lhs: Live<T>, rhs: Live<T>): Live<Unit> = Live.Set(lhs, rhs)

fun <T> liveApplication(kClass: KClass<*>, primitive: Primitive, vararg args: Live<*>): Live<T> =
  Live.Application(kClass, primitive, args.toList())

fun livePause(seconds: Live<Double>): Live<Unit> = Live.Pause(seconds)

fun liveBlock(lives: List<Live<Unit>>): Live<Unit> = Live.Block(lives)

fun <T> liveConditional(
  kClass: KClass<*>, condition: Live<Boolean>,
  trueLive: Live<T>,
  falseLive: Live<T>
): Live<T> = Live.Conditional(kClass, condition, trueLive, falseLive)

fun <T> Live<T>.withArg(index: Int, live: Live<*>): Live<T> =
  asApplication.run {
    Live.Application(kClass, primitive, args.toMutableList().also { it[index] = live }.toList())
  }