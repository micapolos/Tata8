package micapolos.zexy3

sealed class Action : Value {
  object Empty: Action()
  class BoolSet(val variable: Bool, val value: Bool): Action()
  class IntegerSet(val variable: Integer, val value: Integer): Action()
  class NumberSet(val variable: Number, val value: Number): Action()
  class NumberCapture(val variable: Number, val value: Number): Action()
  class TextSet(val variable: Text, val value: Text): Action()
  class ImageSet(val variable: Image, val value: Image): Action()
  class FontSet(val variable: Font, val value: Font): Action()
  class DrawingSet(val variable: Drawing, val value: Drawing): Action()
}