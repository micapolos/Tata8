package micapolos.ast

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


data class Code(val init: String? = null, val update: String? = null)

internal class Translator {
  val codes = mutableListOf<Code>()
  val variableIndices = mutableMapOf<Expression<*>, Int>()

  fun variable(expression: Expression<*>): String = "i${variableIndex(expression)}"

  fun variableIndex(expression: Expression<*>): Int =
    variableIndices[expression] ?: variableIndices.size.also { variableIndex ->
      variableIndices[expression] = variableIndex
      val variable = variable(expression)

      codes += when (expression) {
        is Expression.Constant<*> ->
          Code(init = "int $variable = ${expression.value};")

        is Expression.Variable<*> -> {
          val initializer = variable(expression.initializer)
          Code(init = "int $variable = $initializer;")
        }

        is Expression.Set<*> -> {
          val lhs = variable(expression.lhs)
          val rhs = variable(expression.rhs)
          Code(update = "$lhs = $rhs;")
        }

        is Expression.Application<*> -> {
          val args = expression.args.map { variable(it) }
          when (expression.name) {
            "Int.plus" -> Code(update = "int $variable = ${args[0]} + ${args[1]};")
            "Int.minus" -> Code(update = "int $variable = ${args[0]} - ${args[1]};")
            "Int.times" -> Code(update = "int $variable = ${args[0]} * ${args[1]};")
            "sequence" -> Code()
            "fillRect" -> Code(update = "Game.background.canvas.fillRect(${args[0]}, ${args[1]}, ${args[2]}, ${args[3]});")
            "Int.keepAdding" -> Code(update = "${args[0]} += ${args[1]};")
            "loadImage" -> Code(update = "Game.loadImage(${args[0]}, ${args[1]});")
            "sprite" -> Code(update = "Game.background.canvas.draw(${args[0]}, ${args[1]}, ${args[2]}, ${args[3]}, ${args[4]}, false, false, 1, 1, Composite.NORMAL, 0);")
            else -> error("not implemented: ${expression.name}")
          }
        }
      }
    }
}

val Expression<*>.javaCode: String get() {
  var translator = Translator()
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