package micapolos.zexy

class Animated<T: Value<T>>(val value: Value<T>, val animation: Animation)

fun <T: Drawing<T>> Animated<T>.show () {
  game.with(value).with(animation).show()
}