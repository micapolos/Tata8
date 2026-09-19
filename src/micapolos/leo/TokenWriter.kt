package micapolos.leo

data class TokenWriter(
  val fileName: String,
  var state: State = State.START,
  val atomStringBuilder: StringBuilder = StringBuilder(),
  var modeStack: MutableList<Mode> = mutableListOf(),
  var depth: Int = 0,
  var startLine: Int = 1,
  var startColumn: Int = 1,
  var line: Int = 1,
  var column: Int = 1,
  val tokenProcessor: Processor<Sourced<Token>>,
) : Writer {
  enum class State {
    START,
    INDENT,
    ATOM,
    COLON,
    COMMA,
    CLOSED_PAREN
  }

  enum class Mode {
    BLOCK, COLON, PAREN
  }

  val mode get() = modeStack.lastOrNull() ?: Mode.BLOCK
  val outerMode get() = if (modeStack.size > 1)  modeStack[modeStack.size - 2] else Mode.BLOCK

  val source
    get() = Source(fileName, startLine, startColumn, line, column).also {
      startLine = line
      startColumn = column
    }

  fun <T> sourced(value: T): Sourced<T> =
    Sourced(source, value)

  fun process(token: Token) {
    tokenProcessor.process(sourced(token))
  }

  fun processEnd() {
    modeStack.removeLast()
    process(endToken)
  }

  fun processAtom() {
    process(token(atomStringBuilder.toString()))
    atomStringBuilder.clear()
  }

  fun invalid(name: String) {
    error("Invalid $name at line $line, character $column")
  }

  internal fun updatePosition(char: Char) {
    when {
      char == '\n' -> {
        line++
        column = 1
      }

      else -> {
        column++
      }
    }
  }

  private fun processColonEnds() {
    while (true) {
      when (mode) {
        Mode.COLON -> processEnd()
        Mode.BLOCK, Mode.PAREN -> break
      }
    }
  }

  private fun processUnclosedEnds() {
    while (modeStack.size > depth) {
      processEnd()
    }
  }

  private fun writeAtomStart(char: Char) {
    processUnclosedEnds()
    startLine = line
    startColumn = column
    atomStringBuilder.append(char)
    state = State.ATOM
  }

  private fun writeAtomContinuation(char: Char) {
    atomStringBuilder.append(char)
  }

  private fun writeSpace() {
    when (state) {
      State.START -> {
        while (true) {
          if (depth < modeStack.size) {
            when (modeStack[depth++]) {
              Mode.BLOCK -> break
              Mode.COLON -> {}
              Mode.PAREN -> {}
            }
          } else {
            invalid("indentation")
          }
        }
        state = State.INDENT
      }

      State.ATOM -> {
        writeAtomContinuation(' ')
      }

      State.COLON -> {
        process(beginToken)
        depth++
        modeStack.add(Mode.COLON)
        state = State.START
      }

      State.INDENT, State.COMMA -> {
        state = State.START
      }

      State.CLOSED_PAREN -> {
        invalid("space")
      }
    }
  }

  private fun writeColon() {
    if (mode != Mode.PAREN && outerMode == Mode.PAREN) {
      invalid("colon")
    }
    when (state) {
      State.START -> {}
      State.ATOM -> processAtom()
      State.INDENT, State.COLON, State.COMMA, State.CLOSED_PAREN -> invalid("colon")
    }
    // Mode will be pushed after following space or newline.
    state = State.COLON
  }

  private fun writeOpenParen() {
    when (state) {
      State.START -> {}
      State.ATOM -> processAtom()
      State.INDENT, State.COLON, State.COMMA, State.CLOSED_PAREN -> invalid("opening parenthesis")
    }
    processUnclosedEnds()
    modeStack.add(Mode.PAREN)
    process(beginToken)
    depth++
    state = State.START
  }

  private fun writeCloseParen() {
    when (state) {
      State.START -> {}
      State.ATOM -> processAtom()
      State.CLOSED_PAREN -> {}
      State.INDENT, State.COLON, State.COMMA -> {
        invalid("closing parenthesis")
      }
    }
    processColonEnds()
    state = State.CLOSED_PAREN
  }

  private fun writeComma() {
    when (state) {
      State.ATOM -> processAtom()
      State.CLOSED_PAREN -> {}
      State.START, State.INDENT, State.COLON, State.COMMA -> invalid("comma")
    }
    processColonEnds()
    state = State.COMMA
  }

  private fun writeNewLine() {
    when (state) {
      State.START -> {
        when (mode) {
          Mode.BLOCK -> {}
          Mode.COLON -> invalid("new line")
          Mode.PAREN -> invalid("new line")
        }
      }

      State.ATOM -> processAtom()
      State.COLON -> {
        process(beginToken)
        modeStack.add(Mode.BLOCK)
      }

      State.INDENT, State.COMMA -> invalid("new line")
      State.CLOSED_PAREN -> {}
    }
    depth = 0
    state = State.START
  }

  private fun writeOther(char: Char) {
    when (state) {
      State.START -> when {
        char.isAtomStart -> writeAtomStart(char)
        else -> invalid("atom start character $char")
      }

      State.ATOM -> when {
        char.isAtomContinuation -> writeAtomContinuation(char)
        else -> invalid("atom character $char")
      }

      State.INDENT, State.COLON, State.COMMA, State.CLOSED_PAREN -> {
        invalid("char $char")
      }
    }
  }

  override fun write(char: Char) {
    when (char) {
      ' ' -> writeSpace()
      ':' -> writeColon()
      '(' -> writeOpenParen()
      ')' -> writeCloseParen()
      ',' -> writeComma()
      '\n' -> writeNewLine()
      else -> writeOther(char)
    }

    updatePosition(char)
  }

  override fun done() {
    when (state) {
      State.START -> {}
      State.INDENT, State.ATOM, State.COLON, State.COMMA, State.CLOSED_PAREN -> invalid("end")
    }
    if (depth != 0) {
      invalid("end")
    }
    while (modeStack.size > depth) {
      processEnd()
    }
  }
}

val Char.isAtomStart get() = isLetterOrDigit()
val Char.isAtomContinuation get() = isAtomStart || this == ' '

fun source(string: String) = string.trimIndent().run { if (isNotEmpty()) plus("\n") else this }

fun main() {
  val tokenWriter = TokenWriter("file.leo") { println(it) }
  val string = source(
    """
    circle:
      center(point(x: 10, y: 20))
      radius(10)
    """
  )
  print(string)
  println("=== Tokenizing...")
  tokenWriter.write(string)
  tokenWriter.done()
}