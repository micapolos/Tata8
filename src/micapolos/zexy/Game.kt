package micapolos.zexy

import micapolos.zexy.compiler.Compiler
import micapolos.zexy.compiler.animated
import micapolos.zexy.compiler.compile
import micapolos.zexy.compiler.evaluator
import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexer.Indexer
import micapolos.zexy.indexer.indexed
import micapolos.zexy.model.Action
import micapolos.zexy.runtime.State
import micapolos.zexy.runtime.show
import kotlin.reflect.KClass
import micapolos.zexy.model.Animation as ModelAnimation
import micapolos.zexy.model.Game as ModelGame

data class Game internal constructor(
  val resourcesKClass: KClass<*>,
  internal val model: ModelGame,
)

val game = Game(
  Game::class,
  ModelGame("Game", 480, 256, noDrawing.modelDrawing, ModelAnimation.Once(Action.Empty)))

fun Game.withResources(kClass: KClass<*>): Game = copy(resourcesKClass = kClass)

fun Game.withTitle(title: String): Game = copy(model = model.copy(title = title))

fun <T: Drawing<T>> Game.with(vararg drawings: Value<T>): Game =
  copy(model = model.copy(drawing = stack(*drawings).modelDrawing))

fun Game.with(animation: Animation): Game =
  copy(model = model.copy(animation = animation.model))

fun Game.show() {
  val indexer = Indexer()
  val indexed = indexer.indexed(model)
  val compiler = Compiler(
    resourcesKClass,
    State(
      IntArray(indexer.initialValuesOf(IndexType.INTEGER).size),
      DoubleArray(indexer.initialValuesOf(IndexType.NUMBER).size),
      arrayOfNulls(indexer.initialValuesOf(IndexType.OBJECT).size),
      arrayOfNulls(indexer.initialValues.size)))
  indexer.initialValues.forEachIndexed { index, value ->
    compiler.state.evaluatorArray[index] = compiler.evaluator(value)
  }
  val runtimeGame = compiler.compile(indexed)
  runtimeGame.show()
}

fun main() {
  game
    .withTitle("Sandbox")
    .with(sprite.with(image("/micapolos/quote.png")))
    .with(animation { pause(1) })
    .show()
}
