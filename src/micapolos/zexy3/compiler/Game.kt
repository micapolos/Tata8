package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Game
import micapolos.zexy3.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame =
  RuntimeGame(game.title, animated(game.drawing))
