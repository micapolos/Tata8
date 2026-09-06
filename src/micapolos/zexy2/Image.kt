package micapolos.zexy2

import micapolos.tata8.Image
import micapolos.zexy2.live.Live
import micapolos.zexy2.live.Primitive

val Image.live get() = live(Image::class)

val noImage = null.live(Image::class)

val Live<Image>.size get() =
  Size<Double>(
    Live.Application(Double::class, Primitive.IMAGE_WIDTH, listOf(this)),
    Live.Application(Double::class, Primitive.IMAGE_HEIGHT, listOf(this)))

val Live<Image>.center get() = size.center

val micaFontImage = Image.micaFont.live
val koraFontImage = Image.koraFont.live