package micapolos.zexy2

import micapolos.tata8.Game
import micapolos.tata8.Image
import kotlin.reflect.KClass

fun loadImage(baseClass: KClass<*>, name: String): Image =
  Game.loadImage(baseClass.java, name)
