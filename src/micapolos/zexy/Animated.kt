package micapolos.zexy

class Animated<T : Value<T>>(val value: Value<T>, val animation: Value<Animation>)

fun <T: Value<T>> animated(fn: Animation.Block.() -> Value<T>): Animated<T> {
  val block = Animation.Block()
  val value = block.fn()
  return Animated(value, block.build())
}

fun <T: Value<T>> showValue(fn: Animation.Block.() -> Value<T>) {
  animated(fn).show()
}

@JvmName("showDrawing")
fun <T: Drawing<T>> showDrawing(fn: Animation.Block.() -> Value<T>) {
  animated(fn).show()
}

//@JvmName("showUnit")
//fun show(fn: Animation.Block.() -> Unit) {
//  animated { fn(); noDrawing }.show()
//}

fun <T : Value<T>> Animated<T>.show() {
  game.with(noDrawing).with(parallel(animation, animation { everyStep { this@show.value.log } } )).show()
}

@JvmName("showDrawing")
fun <T : Drawing<T>> Animated<T>.show() {
  game.with(value).with(animation).show()
}