package micapolos.zexy3.indexed

sealed class Image: Value<Image> {
  class Resource(val fileName: String): Image()

  class Render(
    val drawing: Value<Drawing>,
    val width: Int,
    val height: Int
  ): Image()

  class Slice(
    val image: Value<Image>,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
  ): Image()
}
