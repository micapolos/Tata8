package micapolos.zexy.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Variable
import micapolos.zexy.runtime.Animated
import kotlin.reflect.KClass

class Compiler(
  val baseClass: KClass<*> = Compiler::class,
  val intArray: IntArray = intArrayOf(),
  val doubleArray: DoubleArray = doubleArrayOf(),
  val objectArray: Array<Any?> = arrayOf(),
  val animatedValues: Array<Animated<*>?> = arrayOf(),
) {
  val tataImages = mutableMapOf<String, Image>()
  val tataFonts = mutableMapOf<String, Font>()

  fun <T: Value<T>> animatedValueOrNull(variable: Variable<T>): Animated<*>? =
    animatedValues[variable.index]
}

