package micapolos.zexy2

import micapolos.tata8.Font
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

val Font.live: Live<Font> get() = live(Font::class)

fun Live<Font>.width(string: String) = width(string.live)

fun Live<Font>.width(string: Live<String>): Live<Double> =
  Live.Application(Double::class, Primitive.FONT_STRING_WIDTH, listOf(this, string))

val micaFont = Font.mica.live
val koraFont = Font.kora.live