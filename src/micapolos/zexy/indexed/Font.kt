package micapolos.zexy.indexed

sealed class Font: Value {
  class Resource(
    val fileName: String,
    val spaceWidth: Int,
    val charSpacing: Int,
    val lineSpacing: Int
  ) : Font()
}