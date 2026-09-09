package micapolos.zexy3.indexed

sealed class Font: Value<Font> {
  class Resource(
    val fileName: String,
    val spaceWidth: Int,
    val charSpacing: Int,
    val lineSpacing: Int
  ) : Font()
}