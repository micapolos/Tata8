package micapolos.zexy2.ast

import kotlin.reflect.KClass

sealed class Live<out T> {
  abstract val kClass: KClass<*>

  data class Constant<T>(
    override val kClass: KClass<*>,
    val value: T
  ) : Live<T>()

  data class Variable<T>(
    override val kClass: KClass<*>,
    val id: Int,
    val initializer: Live<T>
  ) : Live<T>()

  data class Set<T>(
    val lhs: Live<T>,
    val rhs: Live<T>
  ) : Live<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  data class Application<T>(
    override val kClass: KClass<*>,
    val name: String,
    val args: List<Live<*>>
  ) : Live<T>()

  data class Conditional<T>(
    override val kClass: KClass<*>,
    val condition: Live<Boolean>,
    val trueLive: Live<T>,
    val falseLive: Live<T>,
  ) : Live<T>()
}

val <T> Live<T>.asApplication get() = this as Live.Application<T>