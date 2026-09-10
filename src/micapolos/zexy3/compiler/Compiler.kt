package micapolos.zexy3.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.runtime.Animated
import kotlin.reflect.KClass

class Compiler(
  val baseClass: KClass<*> = Compiler::class,
  val intArray: IntArray = intArrayOf(),
  val doubleArray: DoubleArray = doubleArrayOf(),
  val objectArray: Array<Any?> = arrayOf(),
) {
  val tataImages = mutableMapOf<String, Image>()
  val tataFonts = mutableMapOf<String, Font>()
  val animatedVariables = mutableListOf<Animated<*>>()

  fun <T: Value<T>> animatedVariable(variable: Variable<T>): Animated<*> = animatedVariables[variable.index]
}

