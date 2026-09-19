package micapolos.leo

data class Source(
  val fileName: String,
  val startLine: Int,
  val startColumn: Int,
  val endLine: Int,
  val endColumn: Int,
)
