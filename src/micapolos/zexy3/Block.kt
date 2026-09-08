package micapolos.zexy3

import micapolos.zexy3.dsl.fraction

sealed class Block {
  object Root: Block()
  class Child(val parent: Block): Block()
}

fun <T: Value<T>> main(fn: Block.() -> T): Pair<Block, T> =
  Block.Root.let { scope -> scope to fn(scope) }

fun <T: Value<T>> Block.block(fn: Block.() -> T): Pair<Block, T> =
  Block.Child(this).let { scope -> scope to fn(scope) }

fun main() {
  main {
    val x = newVariable(0.0)
    val y = newVariable(1.0)
    (x + y).fraction
  }
}
