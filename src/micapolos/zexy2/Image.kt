package micapolos.zexy2

import micapolos.tata8.Game
import micapolos.tata8.Image
import micapolos.zexy2.ast.Live
import micapolos.zexy2.ast.Primitive
import kotlin.reflect.KClass

val Image.live get() = constant(Image::class, this)

fun loadImage(baseClass: KClass<*>, name: String): Live<Image> =
  Game.loadImage(baseClass.java, name).live

val Live<Image>.size get() =
  Size<Double>(
    Live.Application(Double::class, Primitive.IMAGE_WIDTH, listOf(this)),
    Live.Application(Double::class, Primitive.IMAGE_HEIGHT, listOf(this)))

val Live<Image>.center get() = size.center

val micaFontImage = constant(Image::class, Image.micaFont)
val koraFontImage = constant(Image::class, Image.koraFont)