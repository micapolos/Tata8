package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Game
import micapolos.zexy3.model.Game as ModelGame

fun Indexer.indexed(game: ModelGame): Game =
  Game(game.title, game.width, game.height, indexed(game.drawing))