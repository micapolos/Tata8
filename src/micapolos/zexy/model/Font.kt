package micapolos.zexy.model

sealed class Font : Value<Font> {
  data class Resource(
    val fileName: String,
    val spaceWidth: Int,
    val charSpacing: Int,
    val lineSpacing: Int
  ) : Font()
}