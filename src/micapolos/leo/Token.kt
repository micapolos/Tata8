package micapolos.leo

sealed class Token {
  enum class Block { INDENT, INLINE, PAREN }

  data class Atom(val string: String) : Token()

  data class Begin(val string: String, val block: Block) : Token()

  data object End : Token()
}

fun token(string: String): Token = Token.Atom(string)
fun beginToken(string: String, block: Token.Block = Token.Block.INDENT): Token = Token.Begin(string, block)
val endToken: Token = Token.End
