package micapolos.ast

import micapolos.tata8.Image
import kotlin.reflect.KClass

fun image(baseClass: KClass<*>, name: String): Expression<Image> =
  Expression.Application(
    Image::class, "loadImage",
    listOf(
      constant(KClass::class, baseClass),
      constant(String::class, name)))
