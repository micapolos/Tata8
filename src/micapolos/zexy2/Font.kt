package micapolos.zexy2

import micapolos.tata8.Font
import micapolos.tata8.Game
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import kotlin.reflect.KClass

fun loadFont(baseClass: KClass<*>, name: String, spaceWidth: Int, glyphSpacing: Int, lineSpacing: Int): Live<Font> =
  constant(Font::class, Game.loadFont(baseClass.java, name, spaceWidth, glyphSpacing, lineSpacing))

fun Live<Font>.width(string: String) = width(constant(String::class, string))

fun Live<Font>.width(string: Live<String>): Live<Double> =
  Live.Application(Double::class, Primitive.FONT_STRING_WIDTH, listOf(this, string))

val Font.live: Live<Font> get() = constant(Font::class, this)

val micaFont = Font.mica.live
val koraFont = Font.kornelka.live