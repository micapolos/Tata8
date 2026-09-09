package micapolos.zexy3.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import kotlin.reflect.KClass

class Compiler(val baseClass: KClass<*>) {
  val tataImages = mutableMapOf<String, Image>()
  val tataFonts = mutableMapOf<String, Font>()
}

