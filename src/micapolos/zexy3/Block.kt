package micapolos.zexy3

sealed class Block<T: Value<T>> {
  object Root: Block<Void>()
  class Child<T: Value<T>>(val parent: Block<*>): Block<T>()
}

fun <T: Value<T>> main(fn: Block<Void>.() -> Void): Void =
  Block.Root.fn()

fun <T: Value<T>> Block<*>.block(fn: Block<*>.() -> T): T =
  Block.Child(this).fn()

fun main() {
  main {
    val x = block {
      val x = newVariable(0.0)
      val y = newVariable(1.0)
      x
    }
    Void
  }
}
