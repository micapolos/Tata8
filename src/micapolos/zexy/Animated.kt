package micapolos.zexy

class Animated<T : Value<T>>(val value: Value<T>, val animation: Value<Animation>)

fun <T : Value<T>> Animated<T>.show() {
  game.with(noDrawing).with(parallel(animation, animation { everyStep { this@show.value.log } } )).show()
}

@JvmName("showDrawing")
fun <T : Drawing<T>> Animated<T>.show() {
  game.with(value).with(animation).show()
}