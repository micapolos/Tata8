package micapolos.zexy.compiler

import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexed.Variable
import micapolos.zexy.runtime.*

fun Compiler.animatedVariable(variable: Variable) =
  Animated(variableEvaluator(variable), ObjectEvaluator { instantAnimation })

fun Compiler.variableEvaluator(variable: Variable): Evaluator<*> = run {
  val typedIndex = variable.typedIndex
  val index = variable.index
  when (variable.indexType) {
    IndexType.INTEGER -> {
      IntEvaluator {
        state.getInt(typedIndex, index)
      }
    }

    IndexType.NUMBER -> {
      DoubleEvaluator {
        state.getDouble(typedIndex, index)
      }
    }

    IndexType.OBJECT -> {
      ObjectEvaluator {
        state.getObject(typedIndex, index)
      }
    }
  }
}

// TODO: Read into animation list by index.
fun Compiler.variableAnimation(variable: Variable): Animation =
  object : Animation {}

fun main() {
  val compiler = Compiler(Compiler::class, State(intArrayOf(), doubleArrayOf(1.0, 10.0, 100.0), arrayOf(), arrayOf(null)))
  val evaluator = compiler.evaluator(Variable(IndexType.NUMBER, 2, 0))
  compiler.state.evaluatorArray[0] = evaluator
  val value = (evaluator as DoubleEvaluator).eval()
  println(value)
}