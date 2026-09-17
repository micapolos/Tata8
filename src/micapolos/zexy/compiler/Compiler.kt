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

fun Compiler.intEvaluator(value: Value) = evaluator(value) as IntEvaluator
fun Compiler.doubleEvaluator(value: Value) = evaluator(value) as DoubleEvaluator
fun <O> Compiler.objectEvaluator(value: Value) = evaluator(value) as ObjectEvaluator<O>
