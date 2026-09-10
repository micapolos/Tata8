package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.Game
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Drawing
import micapolos.zexy3.runtime.Game as RuntimeGame

fun Compiler.compile(game: Game): RuntimeGame =
  RuntimeGame(game.title, animated(game.drawing) as Animated<Drawing>)
