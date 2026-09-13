package micapolos.zexy.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Variable
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.DoubleEvaluator
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.IntEvaluator
import micapolos.zexy.runtime.ObjectEvaluator
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

fun <T: Value<T>> Compiler.evaluator(value: Value<T>): Evaluator<*> = animated(value).evaluator
fun <T: Value<T>> Compiler.intEvaluator(value: Value<T>) = evaluator(value) as IntEvaluator
fun <T: Value<T>> Compiler.doubleEvaluator(value: Value<T>) = evaluator(value) as DoubleEvaluator
fun <T: Value<T>, O> Compiler.objectEvaluator(value: Value<T>) = evaluator(value) as ObjectEvaluator<O>
