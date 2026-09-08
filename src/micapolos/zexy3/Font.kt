package micapolos.zexy3

sealed class Font : Value {
  class Load(val fileName: String, val spaceWidth: Int, val charSpacing: Int, val lineSpacing: Int) : Font()
  class Variable(val initial: Font) : Font()
}