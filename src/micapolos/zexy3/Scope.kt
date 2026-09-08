package micapolos.zexy3

class Scope<T: Value<T>>(val parent: Scope<*>? = null)

fun <T: Value<T>> main(fn: Scope<*>.() -> T): T =
  Scope().fn()

fun <T: Value<T>> Scope<*>.run(fn: Scope<*>.() -> T): T =
  Scope(this).fn()

fun main() {
  main {
    val z = run {
      val x = newVariable(0.0)
      val y = newVariable(1.0)
      x + y
    }
    val z2 = z + z
    z2
  } + 3.0
}
