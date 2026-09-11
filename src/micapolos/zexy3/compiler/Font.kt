package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Font
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.instantAnimation
import micapolos.tata8.Font as TataFont

fun Compiler.animatedFont(font: Font): Animated<TataFont> =
  when (font) {
    is Font.Resource -> {
      val tataFont = tataFonts.computeIfAbsent(font.fileName) {
        Game.loadFont(baseClass.java, font.fileName, font.spaceWidth, font.charSpacing, font.lineSpacing)
      }
      Animated(ObjectEvaluator { tataFont }, instantAnimation)
    }
  }
