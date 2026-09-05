package micapolos.ast

import kotlin.reflect.KClass

sealed class Expression<T> {
  abstract val kClass: KClass<*>

  data class Constant<T>(
    override val kClass: KClass<*>,
    val value: T
  ) : Expression<T>()

  data class Variable<T>(
    override val kClass: KClass<*>,
    val id: Int,
    val initializer: Expression<T>
  ) : Expression<T>()

  data class Set<T>(
    val lhs: Expression<T>,
    val rhs: Expression<T>
  ) : Expression<Unit>() {
    override val kClass: KClass<*> get() = Unit::class
  }

  data class Application<T>(
    override val kClass: KClass<*>,
    val target: Expression<*>?,
    val name: String,
    val args: List<Expression<*>>
  ) : Expression<T>()
}
