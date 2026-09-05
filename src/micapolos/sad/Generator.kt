package micapolos.sad

class Generator {
  internal var lastVariableIndex: Int = 0
  internal val initStrings = ArrayList<String>()
  internal val updateStrings = ArrayList<String>()

  fun add(expression: Expression<*>) {
    if (expression.index == 0) {
      expression.index = -1
      expression.addDeps(this)

      if (expression.declaresVariable) {
        lastVariableIndex++
        expression.index = lastVariableIndex
      }

      expression.initString?.let { initStrings.add(it) }
      expression.updateString?.let { updateStrings += it }
    }
  }

  fun generate(expression: Expression<*>) : String {
    add(expression)
    return compactClassString
  }

  val compactClassString: String get() =
    """
import micapolos.tata8.Game;

fun main() {
  ${initStrings.joinToString("\n").replace("\n", "\n  ")}
  Game.onUpdate = () -> {
    ${updateStrings.joinToString("\n").replace("\n", "\n    ")}
  };
  Game.start();
}
    """.trimIndent()
}