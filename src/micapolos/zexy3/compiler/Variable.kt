package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.DoubleEvaluator
import micapolos.zexy3.runtime.Evaluator
import micapolos.zexy3.runtime.IntEvaluator
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation

fun <T : Value<T>, E> Compiler.evaluator(variable: Variable<T>): Evaluator<E> = run {
  val index = variable.index
  when (variable.indexType) {
    IndexType.INTEGER -> {
      val array = this.intArray
      IntEvaluator { array[index] }
    }
    IndexType.NUMBER -> {
      val array = this.doubleArray
      DoubleEvaluator { array[index] }
    }

    IndexType.OTHER -> {
      val array = this.objectArray
      ObjectEvaluator { array[index] }
    }
  }
} as Evaluator<E>

fun <T : Value<T>> Compiler.animation(variable: Variable<T>): Animation = noAnimation

fun main() {
  val compiler = Compiler(Compiler::class, intArrayOf(), doubleArrayOf(1.0, 10.0, 100.0), arrayOf())
  val animated = compiler.animated<Number, Double>(Variable(IndexType.NUMBER, 2))
  val value = (animated.evaluator as DoubleEvaluator).eval()
  println(value)
}