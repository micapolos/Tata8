package micapolos.zexy2

import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import kotlin.reflect.KClass

val Font.live: Live<Font> get() = live(Font::class)

fun loadFont(
  baseClass: KClass<*>,
  name: String,
  spaceWidth: Int = 2,
  glyphSpacing: Int = 1,
  lineSpacing: Int = 1
): Live<Font> = Game.loadFont(baseClass.java, name, spaceWidth, glyphSpacing, lineSpacing).live

fun Live<Font>.width(string: String) = width(string.live)

fun Live<Font>.width(string: Live<String>): Live<Double> =
  Live.Application(Double::class, Primitive.FONT_STRING_WIDTH, listOf(this, string))

val micaFont = Font.mica.live
val koraFont = Font.kora.live