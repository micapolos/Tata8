package micapolos.zexy2.runtime

interface Drawable {
  val drawing: Drawing
}

fun Drawable.show() {
  drawing.show()
}