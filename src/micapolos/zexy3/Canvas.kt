package micapolos.zexy3

sealed class Canvas : Value {
  class WithImage(val image: Image): Canvas()
}