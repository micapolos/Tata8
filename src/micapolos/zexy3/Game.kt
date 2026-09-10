package micapolos.zexy3

import micapolos.Sandbox
import micapolos.zexy3.compiler.Compiler
import micapolos.zexy3.compiler.animated
import micapolos.zexy3.compiler.compile
import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexer.Indexer
import micapolos.zexy3.indexer.indexed
import micapolos.zexy3.runtime.show
import kotlin.reflect.KClass
import micapolos.zexy3.model.Game as ModelGame

data class Game internal constructor(
  val resourcesKClass: KClass<*>,
  internal val model: ModelGame,
)

val game = Game(Game::class, ModelGame("Game", 480, 256, noDrawing.modelDrawing))

fun Game.withResources(kClass: KClass<*>): Game = copy(resourcesKClass = kClass)
fun Game.withTitle(title: String): Game = copy(model = model.copy(title = title))
fun <T: Drawing<T>> Game.with(vararg drawings: Value<T>): Game =
  copy(model = model.copy(drawing = stack(*drawings).modelDrawing))

fun Game.show() {
  val indexer = Indexer()
  val indexed = indexer.indexed(model)
  val compiler = Compiler(
    resourcesKClass,
    IntArray(indexer.initialValuesOf(IndexType.INTEGER).size),
    DoubleArray(indexer.initialValuesOf(IndexType.NUMBER).size),
    Array(indexer.initialValuesOf(IndexType.OTHER).size) { null })
  indexer.initialValues.forEach {
    compiler.animatedValues.add(compiler.animated(it))
  }
  val runtimeGame = compiler.compile(indexed)
  runtimeGame.show()
}

fun main() {
  val x = variable(10)
  game
    .withResources(Sandbox::class)
    .withTitle("Sandbox")
    .with(sprite.with(image("quote.png")))
    .show()
}