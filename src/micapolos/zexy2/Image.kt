package micapolos.zexy2

import micapolos.tata8.Game
import micapolos.tata8.Image
import kotlin.reflect.KClass

fun loadImage(baseClass: KClass<*>, name: String): Image =
  Game.loadImage(baseClass.java, name)

val Image.center get() =
  Center<Double>(position(size.width.toDouble() * 0.5, size.height.toDouble() * 0.5))