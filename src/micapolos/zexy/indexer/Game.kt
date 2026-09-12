package micapolos.zexy.indexer

import micapolos.zexy.indexed.Game
import micapolos.zexy.model.Game as ModelGame

fun Indexer.indexed(game: ModelGame): Game =
  Game(game.title, game.width, game.height, indexed(game.drawing))