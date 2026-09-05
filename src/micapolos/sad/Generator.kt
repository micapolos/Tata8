package micapolos.sad

class Generator {
  internal var lastIndex: Int = 0
  internal val initStrings = ArrayList<String>()
  internal val updateStrings = ArrayList<String>()

  fun add(expression: Expression<*>) {
    if (expression.index == 0) {
      expression.index = -1
      expression.addDeps(this)

      lastIndex++
      expression.index = lastIndex

      val initString = expression.initString
      if (initString != null) {
        initStrings.add(initString)
      }

      val updateString = expression.updateString
      if (updateString != null) {
        updateStrings.add(updateString)
      }
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
}
    """.trimIndent()
}