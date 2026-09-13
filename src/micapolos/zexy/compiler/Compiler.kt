package micapolos.zexy.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Variable
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.State
import kotlin.reflect.KClass

class Compiler(
  val baseClass: KClass<*> = Compiler::class,
  val state: State = State(),
) {
  val tataImages = mutableMapOf<String, Image>()
  val tataFonts = mutableMapOf<String, Font>()

  fun <T: Value<T>> animatedValueOrNull(variable: Variable<T>): Animated<*>? =
    state.animatedArray[variable.index]
}

