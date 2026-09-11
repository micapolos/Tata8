package micapolos.zexy3.indexed

sealed class Image: Value<Image> {
  object Empty: Image()

  class Resource(val fileName: String): Image()
}
