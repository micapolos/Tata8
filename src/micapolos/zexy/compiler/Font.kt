package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Font
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.tata8.Font as TataFont

fun Compiler.fontEvaluator(font: Font): Evaluator<TataFont> =
  when (font) {
    is Font.Resource -> {
      val tataFont = tataFonts.computeIfAbsent(font.fileName) {
        Game.loadFont(baseClass.java, font.fileName, font.spaceWidth, font.charSpacing, font.lineSpacing)
      }
      ObjectEvaluator { tataFont }
    }
  }
