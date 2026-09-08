package micapolos.zexy3

sealed class Image : Value {
  class Load(val fileName: String): Image()
  class Create(val width: Int, val height: Int): Image()
  class Variable(val initial: Image): Image()
  class Slice(val image: Image, val x: Integer, val y: Integer, val width: Integer, val height: Integer): Image()
}