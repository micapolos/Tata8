package micapolos.zexy3

sealed class Image : Value<Image> {
  class Resource(val fileName: String): Image()

  class Render(
    val drawing: Value<Drawing>,
    val width: Int,
    val height: Int
  ): Image()

  class Slice(
    val image: Value<Image>,
    val x: Value<Integer>,
    val y: Value<Integer>,
    val width: Value<Integer>,
    val height: Value<Integer>
  ): Image()
}