package micapolos.zexy.compiler

import micapolos.zexy.indexed.IndexType
import micapolos.zexy.indexed.Value
import micapolos.zexy.indexed.Variable
import micapolos.zexy.runtime.*

fun <T: Value<T>> Compiler.animatedVariable(variable: Variable<T>) =
  Animated(variableEvaluator(variable), variableAnimation(variable))

fun <T : Value<T>> Compiler.variableEvaluator(variable: Variable<T>): Evaluator<*> = run {
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
fun <T : Value<T>> Compiler.variableAnimation(variable: Variable<T>): Animation =
  object : Animation {
    override fun start() {
      val animatedValue = animatedValueOrNull(variable)
      if (animatedValue != null) {
        animatedValue.animation.start()
      }
    }

    override fun step(seconds: Double): Double {
      val animatedValue = animatedValueOrNull(variable)
      if (animatedValue != null) {
        return animatedValue.animation.step(seconds)
      } else {
        return seconds
      }
    }
  }

fun main() {
  val compiler = Compiler(Compiler::class, State(intArrayOf(), doubleArrayOf(1.0, 10.0, 100.0), arrayOf(), arrayOf(null)))
  val animated = compiler.animated(Variable(IndexType.NUMBER, 2, 0))
  compiler.state.animatedArray[0] = animated
  val value = (animated.evaluator as DoubleEvaluator).eval()
  println(value)
}