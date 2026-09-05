package micapolos.ast

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


data class JavaCode(val init: String? = null, val update: String? = null)

internal class JavaTranslator {
  val codes = mutableListOf<JavaCode>()
  val variableIndices = mutableMapOf<Expression<*>, Int>()

  fun variable(expression: Expression<*>): String = "i${variableIndex(expression)}"

  fun variableIndex(expression: Expression<*>): Int =
    variableIndices[expression] ?: variableIndices.size.also { variableIndex ->
      variableIndices[expression] = variableIndex
      val variable = variable(expression)

      codes += when (expression) {
        is Expression.Constant<*> ->
          JavaCode(init = "int $variable = ${expression.value};")

        is Expression.Variable<*> -> {
          val initializer = variable(expression.initializer)
          JavaCode(init = "int $variable = $initializer;")
        }

        is Expression.Set<*> -> {
          val lhs = variable(expression.lhs)
          val rhs = variable(expression.rhs)
          JavaCode(update = "$lhs = $rhs;")
        }

        is Expression.Application<*> -> {
          val args = expression.args.map { variable(it) }
          when (expression.name) {
            "Int.plus" -> JavaCode(update = "int $variable = ${args[0]} + ${args[1]};")
            "Int.minus" -> JavaCode(update = "int $variable = ${args[0]} - ${args[1]};")
            "Int.times" -> JavaCode(update = "int $variable = ${args[0]} * ${args[1]};")
            "Double.plus" -> JavaCode(update = "int $variable = ${args[0]} + ${args[1]};")
            "Double.minus" -> JavaCode(update = "int $variable = ${args[0]} - ${args[1]};")
            "Double.times" -> JavaCode(update = "int $variable = ${args[0]} * ${args[1]};")
            "sequence" -> JavaCode()
            "fillRect" -> JavaCode(update = "Game.background.canvas.fillRect(${args[0]}, ${args[1]}, ${args[2]}, ${args[3]});")
            "Int.keepAdding" -> JavaCode(update = "${args[0]} += ${args[1]};")
            "Double.keepAdding" -> JavaCode(update = "${args[0]} += ${args[1]};")
            "loadImage" -> JavaCode(update = "Game.loadImage(${args[0]}, ${args[1]});")
            "sprite" -> JavaCode(update = "Game.background.canvas.draw(${args[0]}, ${args[1]}, ${args[2]}, ${args[3]}, ${args[4]}, false, false, 1, 1, Composite.NORMAL, 0);")
            else -> error("not implemented: ${expression.name}")
          }
        }
      }
    }
}

val Expression<*>.javaCode: String get() {
  var translator = JavaTranslator()
  translator.variableIndex(this)
  return """
import micapolos.tata8.Game;
import micapolos.tata8.Composite;

${translator.codes.mapNotNull { it.init }.joinToString("\n")}

void main() {
  Game.onUpdate = () -> {
    Game.background.canvas.clear();
    ${translator.codes.mapNotNull { it.update }.joinToString("\n").replace("\n", "\n    ")}
  };
  Game.start();
}
  """.trimIndent()
}

fun Expression<*>.writeJava(fileName: String) {
  val path: Path = Paths.get(fileName)
  Files.writeString(
    Paths.get(fileName),
    javaCode,
    StandardOpenOption.CREATE,
    StandardOpenOption.TRUNCATE_EXISTING
  )
}