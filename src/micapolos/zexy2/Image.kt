package micapolos.zexy2

import micapolos.tata8.Game
import micapolos.tata8.Image
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive
import kotlin.reflect.KClass

val Image.live get() = live(Image::class)

fun loadImage(baseClass: KClass<*>, name: String): Live<Image> =
  Game.loadImage(baseClass.java, name).live

val Live<Image>.size get() =
  Size<Double>(
    Live.Application(Double::class, Primitive.IMAGE_WIDTH, listOf(this)),
    Live.Application(Double::class, Primitive.IMAGE_HEIGHT, listOf(this)))

val Live<Image>.center get() = size.center

val micaFontImage = Image.micaFont.live
val koraFontImage = Image.koraFont.live