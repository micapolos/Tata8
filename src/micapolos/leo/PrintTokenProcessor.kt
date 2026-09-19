package micapolos.leo

class PrintTokenProcessor(
  var first: Boolean = true,
  val blockStack: MutableList<Token.Block> = mutableListOf(),
) : Processor<Token> {
  val mode get() = blockStack.lastOrNull() ?: Token.Block.INDENT

  private fun writeIndent() {
    if (mode == Token.Block.INDENT) {
      blockStack.forEach { block ->
        when (block) {
          Token.Block.INDENT -> print("  ")
          Token.Block.INLINE,
          Token.Block.PAREN -> {
          }
        }
      }
    }
  }

  private fun writeComma() {
    when (mode) {
      Token.Block.INDENT -> {}
      Token.Block.INLINE,
      Token.Block.PAREN -> if (first) {
        first = false
      } else {
        print(", ")
      }
    }
  }

  override fun process(value: Token) {
    when (value) {
      is Token.Atom -> {
        writeIndent()
        writeComma()
        print(value.string)
      }

      is Token.Begin -> {
        writeIndent()
        writeComma()
        print(value.string)
        when (value.block) {
          Token.Block.INDENT -> print(":\n")
          Token.Block.INLINE -> print(": ")
          Token.Block.PAREN -> print("(")
        }
        blockStack.add(value.block)
        first = true
      }

      is Token.End -> {
        val block = blockStack.removeLast()
        when (block) {
          Token.Block.INDENT -> print("\n")
          Token.Block.INLINE -> {}
          Token.Block.PAREN -> print(")")
        }
        first = false
      }
    }
  }
}

fun main() {
  val tokenProcessor = PrintTokenProcessor()
  val tokenWriter = TokenWriter("file.leo") { tokenProcessor.process(it.value) }
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