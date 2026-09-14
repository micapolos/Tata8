package micapolos.zexy.model

sealed class Image : Value<Image> {
  data object Empty : Image()

  data class Resource(val fileName: String): Image()
}