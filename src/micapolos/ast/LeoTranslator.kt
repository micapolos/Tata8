package micapolos.ast

import micapolos.Camel
import micapolos.Leo.leo
import kotlin.reflect.KClass

data class LeoCode(val init: String? = null, val update: String? = null)

internal class LeoTranslator {
  val codes = mutableListOf<LeoCode>()
  val variableIndices = mutableMapOf<Expression<*>, Int>()

  fun variable(expression: Expression<*>): String = "i${variableIndex(expression)}"

  fun leoLiteral(x: Any?) = when (x) {
    is String -> "\"$x\""
    is KClass<*> -> Camel.camelToSpaced(x.simpleName)
    else -> "$x"
  }

  fun leoVariable(kClass: KClass<*>, index: Int) =
    leo("${leoLiteral(kClass)} $index")

  fun variableIndex(expression: Expression<*>): Int =
    variableIndices[expression] ?: variableIndices.size.also { variableIndex ->
      variableIndices[expression] = variableIndex
      val variable = leoVariable(expression.kClass, variableIndex)

      codes += when (expression) {
        is Expression.Constant<*> ->
          LeoCode(init = leo(variable, leoLiteral(expression.value)))

        is Expression.Variable<*> -> {
          val initializer = variable(expression.initializer)
          LeoCode(init = leo("variable", leo(variable, initializer)))
        }

        is Expression.Set<*> -> {
          val lhs = variable(expression.lhs)
          val rhs = variable(expression.rhs)
          LeoCode(update = leo("set", lhs, rhs))
        }

        is Expression.Application<*> -> {
          val args = expression.args.map { variable(it) }
          when (expression.name) {
            "Int.plus", "Double.plus" -> LeoCode(update = leo(variable, leo("add", leo(args[0], args[1]))))
            "Int.minus", "Double.minus" -> LeoCode(update = leo(variable, leo("subtract", leo(args[0], args[1]))))
            "Int.times", "Double.times" -> LeoCode(update = leo(variable, leo("multiply", leo(args[0], args[1]))))
            "Int.keepAdding", "Double.keepAdding" -> LeoCode(update = leo("keep adding", leo(args[0], args[1])))
            "sequence" -> LeoCode()
            "loadImage" -> LeoCode(update = leo("load image", args[0], args[1]))
            "sprite" -> LeoCode(update = leo("draw",
              leo(args[0]),
              leo("anchor", leo("x", args[1]), leo("y", args[2])),
              leo("position", leo("x", args[3]), leo("y", args[4])),
              leo("flip", leo("x", args[5]), leo("y", args[6])),
              leo("scale", leo("x", args[7]), leo("y", args[8])),
              leo("composite", args[9]),
              leo("angle", args[10])))
            else -> error("not implemented: ${expression.name}")
          }
        }
      }
    }
}

val Expression<*>.leoCode: String get() {
  var translator = LeoTranslator()
  translator.variableIndex(this)
  return leo("game",
    *translator.codes.mapNotNull { it.init }.toTypedArray(),
    leo("animate", *translator.codes.mapNotNull { it.update }.toTypedArray()))
}
