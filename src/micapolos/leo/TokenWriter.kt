package micapolos.leo

data class TokenWriter(
  val fileName: String,
  var state: State = State.START,
  val atomStringBuilder: StringBuilder = StringBuilder(),
  var blockStack: MutableList<Token.Block> = mutableListOf(),
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

  val block get() = blockStack.lastOrNull() ?: Token.Block.INDENT
  val outerMode get() = if (blockStack.size > 1)  blockStack[blockStack.size - 2] else Token.Block.INDENT

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
    blockStack.removeLast()
    process(endToken)
  }

  fun processAtom() {
    process(token(atomStringBuilder.toString()))
    atomStringBuilder.clear()
  }

  fun processBegin(block: Token.Block) {
    process(beginToken(atomStringBuilder.toString(), block))
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
      when (block) {
        Token.Block.INLINE -> processEnd()
        Token.Block.INDENT, Token.Block.PAREN -> break
      }
    }
  }

  private fun processUnclosedEnds() {
    while (blockStack.size > depth) {
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
          if (depth < blockStack.size) {
            when (blockStack[depth++]) {
              Token.Block.INDENT -> break
              Token.Block.INLINE, Token.Block.PAREN -> {}
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
        processBegin(Token.Block.INLINE)
        depth++
        blockStack.add(Token.Block.INLINE)
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
    if (block == Token.Block.INLINE && outerMode == Token.Block.PAREN) {
      invalid("second colon after opening parenthesis")
    }
    when (state) {
      State.START -> {}
      State.ATOM -> {}
      State.INDENT, State.COLON, State.COMMA, State.CLOSED_PAREN -> invalid("colon")
    }
    // Begin token and mode will be pushed after following space or newline.
    state = State.COLON
  }

  private fun writeOpenParen() {
    when (state) {
      State.START -> {}
      State.ATOM -> {}
      State.INDENT, State.COLON, State.COMMA, State.CLOSED_PAREN -> invalid("opening parenthesis")
    }
    processUnclosedEnds()
    blockStack.add(Token.Block.PAREN)
    processBegin(Token.Block.PAREN)
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
        when (block) {
          Token.Block.INDENT -> {}
          Token.Block.INLINE, Token.Block.PAREN -> invalid("new line")
        }
      }

      State.ATOM -> processAtom()
      State.COLON -> {
        processBegin(Token.Block.INDENT)
        blockStack.add(Token.Block.INDENT)
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
    while (blockStack.size > depth) {
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
    numbers: 10, 20, 30
    digit names:
      0: zero
      1: one
      2: two
    letter names(a: ala, b: bartek)
    """
  )
  print(string)
  println("=== Tokenizing...")
  tokenWriter.write(string)
  tokenWriter.done()
}