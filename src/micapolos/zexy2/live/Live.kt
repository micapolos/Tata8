package micapolos.zexy2.live

import kotlin.reflect.KClass

sealed class Live<out T> {
  abstract val kClass: KClass<*>

  object Bottom : Live<Nothing>() {
    override val kClass = Nothing::class
  }

  data class Constant<out T>(
    override val kClass: KClass<*>,
    val value: T
  ) : Live<T>()

  data class Variable<out T>(
    override val kClass: KClass<*>,
    val initializer: Live<T>
  ) : Live<T>()

  data class Set<out T>(
    val lhs: Live<T>,
    val rhs: Live<T>
  ) : Live<Animation>() {
    override val kClass: KClass<*> get() = Animation::class
  }

  data class Application<out T>(
    override val kClass: KClass<*>,
    val primitive: Primitive,
    val args: List<Live<*>>
  ) : Live<T>()

  data class Conditional<out T>(
    override val kClass: KClass<*>,
    val condition: Live<Boolean>,
    val trueLive: Live<T>,
    val falseLive: Live<T>,
  ) : Live<T>()
}

val liveBottom: Live<Nothing> = Live.Bottom

fun <T> liveConstant(kClass: KClass<*>, t: T): Live<T> = Live.Constant(kClass, t)

fun <T> liveVariable(kClass: KClass<*>, initializer: Live<T>): Live<T> = Live.Variable(kClass, initializer)

fun <T> liveSet(lhs: Live<T>, rhs: Live<T>): Live<Animation> = Live.Set(lhs, rhs)

fun <T> liveApplication(kClass: KClass<*>, primitive: Primitive, vararg args: Live<*>): Live<T> =
  Live.Application(kClass, primitive, args.toList())

fun <T> liveConditional(
  kClass: KClass<*>, condition: Live<Boolean>,
  trueLive: Live<T>,
  falseLive: Live<T>
): Live<T> = Live.Conditional(kClass, condition, trueLive, falseLive)

val <T> Live<T>.asApplication get() = this as Live.Application<T>

fun <T> Live<T>.withArg(index: Int, live: Live<*>): Live<T> =
  asApplication.run {
    copy(args = args.toMutableList().also { it[index] = live }.toList())
  } as Live<T>