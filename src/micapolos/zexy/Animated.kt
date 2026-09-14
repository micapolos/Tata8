package micapolos.zexy

class Animated<T: Value<T>>(val value: Value<T>, val animation: Animation)

fun <T: Value<T>> Animated<T>.show () {
  game.with(noDrawing.apply { value.logged }).with(animation).show()
}

@JvmName("showDrawing")
fun <T: Drawing<T>> Animated<T>.show() {
  game.with(value).with(animation).show()
}