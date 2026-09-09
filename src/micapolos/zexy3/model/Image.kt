package micapolos.zexy3.model

sealed class Image : Value<Image> {
  class Resource(val fileName: String): Image()

  class Render(
    val drawing: Value<Drawing>,
    val width: Int,
    val height: Int
  ): Image()

  class Slice(
    val image: Value<Image>,
    val x: Value<ModelInteger>,
    val y: Value<ModelInteger>,
    val width: Value<ModelInteger>,
    val height: Value<ModelInteger>
  ): Image()
}