package micapolos.zexy.model

sealed class Image : Value {
  data object Empty : Image()

  data class Resource(val fileName: String): Image()
}