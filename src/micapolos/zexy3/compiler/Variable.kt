package micapolos.zexy3.compiler

import micapolos.zexy3.indexed.IndexType
import micapolos.zexy3.indexed.Value
import micapolos.zexy3.indexed.Variable
import micapolos.zexy3.runtime.*

fun <T: Value<T>> Compiler.animatedVariable(variable: Variable<T>) =
  Animated(variableEvaluator(variable), variableAnimation(variable))

fun <T : Value<T>> Compiler.variableEvaluator(variable: Variable<T>): Evaluator<*> = run {
  val typedIndex = variable.typedIndex
  val index = variable.index
  val animatedValues = this.animatedValues
  when (variable.indexType) {
    IndexType.INTEGER -> {
      val array = this.intArray
      IntEvaluator {
        animatedValues[index].let { animatedValue ->
          if (animatedValue == null) {
            array[typedIndex]
          } else {
            (animatedValue.evaluator as IntEvaluator).eval()
          }
        }
      }
    }

    IndexType.NUMBER -> {
      val array = this.doubleArray
      DoubleEvaluator {
        animatedValues[index].let { animatedValue ->
          if (animatedValue == null) {
            array[typedIndex]
          } else {
            (animatedValue.evaluator as DoubleEvaluator).eval()
          }
        }
      }
    }

    IndexType.OTHER -> {
      val array = this.objectArray
      ObjectEvaluator {
        animatedValues[index].let { animatedValue ->
          if (animatedValue == null) {
            array[typedIndex]
          } else {
            (animatedValue.evaluator as ObjectEvaluator).eval()
          }
        }
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
        return 0.0
      }
    }
  }

fun main() {
  val compiler = Compiler(Compiler::class, intArrayOf(), doubleArrayOf(1.0, 10.0, 100.0), arrayOf())
  val animated = compiler.animated(Variable(IndexType.NUMBER, 2, 0))
  compiler.animatedValues.add(animated)
  val value = (animated.evaluator as DoubleEvaluator).eval()
  println(value)
}