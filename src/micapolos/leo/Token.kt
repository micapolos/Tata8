package micapolos.leo

sealed class Token {
  data class Atom(val string: String): Token()
  data object Begin: Token()
  data object End: Token()
}

fun token(string: String): Token = Token.Atom(string)
val beginToken: Token = Token.Begin
val endToken: Token = Token.End
