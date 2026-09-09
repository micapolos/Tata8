package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Font
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation
import micapolos.tata8.Font as TataFont

fun Compiler.animation(font: Font): Animation =
  when (font) {
    is Font.Resource -> noAnimation
  }

fun Compiler.evaluator(font: Font): ObjectEvaluator<TataFont> =
  when (font) {
    is Font.Resource -> {
      val tataFont = tataFonts.computeIfAbsent(font.fileName) {
        Game.loadFont(baseClass.java, font.fileName, font.spaceWidth, font.charSpacing, font.lineSpacing)
      }
      ObjectEvaluator { tataFont }
    }
  }