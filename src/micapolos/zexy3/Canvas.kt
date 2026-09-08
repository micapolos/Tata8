package micapolos.zexy3

sealed class Canvas : Value<Canvas> {
  class WithImage(val image: Image): Canvas()
}