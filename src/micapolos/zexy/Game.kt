package micapolos.zexy

import micapolos.zexy.compiler.Compiler
import micapolos.zexy.compiler.animated
import micapolos.zexy.compiler.compile
import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexer.Indexer
import micapolos.zexy.indexer.indexed
import micapolos.zexy.runtime.show
import kotlin.reflect.KClass
import micapolos.zexy.model.Game as ModelGame

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
    Array(indexer.initialValuesOf(IndexType.OBJECT).size) { null })
  indexer.initialValues.forEach {
    compiler.animatedValues.add(compiler.animated(it))
  }
  val runtimeGame = compiler.compile(indexed)
  runtimeGame.show()
}

fun main() {
  val x = variable(10)
  game
    .withTitle("Sandbox")
    .with(sprite.with(image("/micapolos/quote.png")))
    .show()
}