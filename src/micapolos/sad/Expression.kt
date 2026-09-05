package micapolos.sad

abstract class Expression<T> {
  internal var index: Int = 0

  internal open val declaresVariable: Boolean get() = false
  internal open val isVariable: Boolean get() = false
  internal open val initString: String? = null
  internal open val updateString: String? = null
  internal abstract fun addDeps(generator: Generator)

  open val refString: String get() = "v$index"

  fun checkVariable() {
    if (!isVariable) {
      throw RuntimeException("Not variable")
    }
  }
}

fun int(i: Int): Expression<Int> = object : Expression<Int>() {
  override val refString: String get() = "$i"
  override fun addDeps(generator: Generator) {}
}

operator fun Expression<Int>.unaryMinus(): Expression<Int> = object : Expression<Int>() {
  override val declaresVariable = true
  override val updateString: String? get() = "int $refString = -${this@unaryMinus.refString};"
  override fun addDeps(generator: Generator) {
    generator.add(this@unaryMinus)
  }
}

operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> = object : Expression<Int>() {
  override val declaresVariable = true
  override val updateString: String get() = "int $refString = ${this@plus.refString} + ${expression.refString};"
  override fun addDeps(generator: Generator) {
    generator.add(this@plus)
    generator.add(expression)
  }
}

fun newInt() = newInt(0)
fun newInt(i: Int) = newInt(int(i))
fun newInt(expression: Expression<Int>): Expression<Int> = object : Expression<Int>() {
  override val isVariable: Boolean get() = true
  override val declaresVariable = true
  override val initString: String get() = "int $refString = ${expression.refString};"
  override fun addDeps(generator: Generator) {
    generator.add(expression)
  }
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Nothing> = object : Expression<Nothing>() {
  override val updateString: String get() = "${this@set.refString} = ${expression.refString};"
  override fun addDeps(generator: Generator) {
    generator.add(this@set)
    generator.add(expression)
  }
}.also { checkVariable() }

fun sequence(vararg expressions: Expression<Nothing>): Expression<Nothing> = object : Expression<Nothing>() {
  override fun addDeps(generator: Generator) {
    for (expression in expressions) {
      generator.add(expression)
    }
  }
}

fun fillRect(x: Expression<Int>, y: Expression<Int>, width: Expression<Int>, height: Expression<Int>): Expression<Nothing> = object : Expression<Nothing>() {
  override val updateString: String get() =
    "Game.background.canvas.fillRect(${x.refString}, ${y.refString}, ${width.refString}, ${height.refString});"

  override fun addDeps(generator: Generator) {
    generator.add(x)
    generator.add(y)
    generator.add(width)
    generator.add(height)
  }
}

