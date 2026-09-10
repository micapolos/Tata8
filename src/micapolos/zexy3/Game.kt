package micapolos.zexy3

import micapolos.Sandbox
import micapolos.zexy3.compiler.Compiler
import micapolos.zexy3.compiler.compile
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexer.Indexer
import micapolos.zexy3.indexer.indexed
import micapolos.zexy3.runtime.show
import kotlin.reflect.KClass
import micapolos.zexy3.model.Game as ModelGame

class Game internal constructor(
  val baseClass: KClass<*>,
  internal val model: ModelGame,
)

fun game(
  resourcesClass: KClass<*> = Game::class,
  title: String = "Zexy game",
  drawing: Value<Drawing> = noDrawing,
) = Game(resourcesClass, ModelGame(title, 480, 256, drawing.modelDrawing))

fun Game.show() {
  val indexer = Indexer()
  val indexed = indexer.indexed(model)
  val compiler = Compiler(
    baseClass,
    IntArray(indexer.initialValuesOf(IndexType.INTEGER).size),
    DoubleArray(indexer.initialValuesOf(IndexType.NUMBER).size),
    Array(indexer.initialValuesOf(IndexType.OTHER).size) { null })
  val runtime = compiler.compile(indexed)
  runtime.show()
}

fun main() {
  game(
    resourcesClass = Sandbox::class,
    drawing = stack(
      rect(10.value, 10.value, 30.value, 30.value),
      sprite(image("quote.png"), 10, 10),
      sprite(image("quote.png"), 60, 60))).show()
}