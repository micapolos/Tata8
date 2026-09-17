package micapolos.zexy.indexed

sealed class Image: Value {
  object Empty: Image()

  class Resource(val fileName: String): Image()
}
