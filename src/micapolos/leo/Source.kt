package micapolos.leo

data class Source(
  val fileName: String = "",
  val startLine: Int = 1,
  val startColumn: Int = 1,
  val endLine: Int = 1,
  val endColumn: Int = 1,
)
