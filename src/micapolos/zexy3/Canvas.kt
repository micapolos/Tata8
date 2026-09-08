package micapolos.zexy3

sealed class Canvas {
  class WithImage(val image: Image): Canvas()
}