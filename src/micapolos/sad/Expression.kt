package micapolos.sad

abstract class Expression<T> {
  internal var index: Int = 0

  internal abstract val initString: String?
  internal abstract val updateString: String?
  internal abstract val refString: String
  internal abstract fun addDeps(generator: Generator)
}

fun int(i: Int): Expression<Int> = object : Expression<Int>() {
  override val initString: String? get() = null
  override val updateString: String? get() = null
  override val refString: String get() = "$i"
  override fun addDeps(generator: Generator) {}
}

operator fun Expression<Int>.unaryMinus(): Expression<Int> = object : Expression<Int>() {
  override val initString: String? get() = null
  override val updateString: String? get() = "int $refString = -${this@unaryMinus.refString};"
  override val refString: String get() = "v$index"
  override fun addDeps(generator: Generator) {
    generator.add(this@unaryMinus)
  }
}

operator fun Expression<Int>.plus(expression: Expression<Int>): Expression<Int> = object : Expression<Int>() {
  override val initString: String? get() = null
  override val updateString: String? get() = "int $refString = ${this@plus.refString} + ${expression.refString};"
  override val refString: String get() = "v$index"
  override fun addDeps(generator: Generator) {
    generator.add(this@plus)
    generator.add(expression)
  }
}

fun newInt(expression: Expression<Int>): Expression<Int> = object : Expression<Int>() {
  override val initString: String? get() = "int $refString = ${expression.refString};"
  override val updateString: String? get() = null
  override val refString: String get() = "v$index"
  override fun addDeps(generator: Generator) {
    generator.add(expression)
  }
}

fun <T> Expression<T>.set(expression: Expression<T>): Expression<Nothing> = object : Expression<Nothing>() {
  override val initString: String? get() = null
  override val updateString: String? get() = "${this@set.refString} = ${expression.refString};"
  override val refString: String get() = "v$index"
  override fun addDeps(generator: Generator) {
    generator.add(this@set)
    generator.add(expression)
  }
}

fun sequence(vararg expressions: Expression<Nothing>): Expression<Nothing> = object : Expression<Nothing>() {
  override val initString: String get() =
    expressions.asSequence().mapNotNull { it.initString }.joinToString("\n")
  override val updateString: String get() =
    expressions.asSequence().mapNotNull { it.updateString }.joinToString("\n")
  override val refString: String get() = TODO()

  override fun addDeps(generator: Generator) {
    for (unit in expressions) {
      generator.add(unit)
    }
  }
}

fun fillRect(x: Expression<Int>, y: Expression<Int>, width: Expression<Int>, height: Expression<Int>): Expression<Nothing> = object : Expression<Nothing>() {
  override val initString: String? get() = null
  override val updateString: String get() = "Game.background.canvas.fillRect(${x.refString}, ${y.refString}, ${width.refString}, ${height.refString});"
  override val refString: String get() = TODO()

  override fun addDeps(generator: Generator) {
    generator.add(x)
    generator.add(y)
    generator.add(width)
    generator.add(height)
  }
}

