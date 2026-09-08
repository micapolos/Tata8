package micapolos.zexy3

class Block<T: Value<T>>(val parent: Block<*>? = null)

fun <T: Value<T>> main(fn: Block<*>.() -> T): T =
  Block().fn()

fun <T: Value<T>> Block<*>.block(fn: Block<*>.() -> T): T =
  Block(this).fn()

fun main() {
  main {
    val z = block {
      val x = newVariable(0.0)
      val y = newVariable(1.0)
      x + y
    }
    val z2 = z + z
    z2
  } + 3.0
}
