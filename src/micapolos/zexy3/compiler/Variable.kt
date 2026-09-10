package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.runtime.*

fun <T : Value<T>> Compiler.variableEvaluator(variable: Variable<T>): Evaluator<*> = run {
  val index = variable.typedIndex
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
}

// TODO: Read into animation list by index.
fun <T : Value<T>> Compiler.variableAnimation(variable: Variable<T>): Animation =
  object : Animation {
    override fun start() {
      animatedVariable(variable).animation.start()
    }

    override fun step(seconds: Double): Double {
      return animatedVariable(variable).animation.step(seconds)
    }
  }

fun main() {
  val compiler = Compiler(Compiler::class, intArrayOf(), doubleArrayOf(1.0, 10.0, 100.0), arrayOf())
  val animated = compiler.animated(Variable(IndexType.NUMBER, 2, 0))
  compiler.animatedVariables.add(animated)
  val value = (animated.evaluator as DoubleEvaluator).eval()
  println(value)
}