package micapolos.zexy.evaluator

import micapolos.tata8.Font
import micapolos.tata8.Image
import micapolos.zexy.runtime.Drawing
import java.nio.ByteBuffer

class State(
  val buffer: ByteBuffer,
  val images: Array<Image>,
  val fonts: Array<Font>,
  val drawings: Array<Drawing>,
) {
  fun int(index: Int): Int = buffer.getInt(index)
  fun double(index: Int): Double = buffer.getDouble(index)
}