package micapolos.zexy.compiler

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy.indexed.Value
import micapolos.zexy.runtime.*
import kotlin.reflect.KClass

class Compiler(
  val baseClass: KClass<*> = Compiler::class,
  val state: State = State(),
) {
  val tataImages = mutableMapOf<String, Image>()
  val tataFonts = mutableMapOf<String, Font>()
}

fun <T: Value<T>> Compiler.evaluator(value: Value<T>): Evaluator<*> = animated(value).evaluator
fun <T: Value<T>> Compiler.intEvaluator(value: Value<T>) = evaluator(value) as IntEvaluator
fun <T: Value<T>> Compiler.doubleEvaluator(value: Value<T>) = evaluator(value) as DoubleEvaluator
fun <T: Value<T>, O> Compiler.objectEvaluator(value: Value<T>) = evaluator(value) as ObjectEvaluator<O>
