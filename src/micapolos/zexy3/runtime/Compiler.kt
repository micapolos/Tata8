package micapolos.zexy3.runtime

import micapolos.DepressedChicken
import micapolos.tata8.Game
import micapolos.zexy3.Drawing
import micapolos.zexy3.Image
import micapolos.zexy3.Integer
import micapolos.zexy3.Number
import java.util.function.Supplier
import kotlin.math.floor

class Compiler(val baseClass: Class<*>) {
  val loadedImages: MutableMap<String, micapolos.tata8.Image> = mutableMapOf()

  fun supplier(image: Image): Supplier<micapolos.tata8.Image> =
    when (image) {
      is Image.Create -> TODO()
      is Image.Load ->
        loadedImages.getOrPut(image::class.java.name) {
          IO.println("Loading image: ${image.fileName}")
          Game.loadImage(baseClass, image.fileName)
        }.let { image ->
          Supplier { image }
        }

      is Image.Slice -> TODO()
      is Image.Variable -> TODO()
    }

  fun runnable(drawing: Drawing) =
    when (drawing) {
      is Drawing.Rotate -> TODO()
      is Drawing.Scale -> TODO()
      is Drawing.Select -> TODO()
      is Drawing.Stack -> TODO()
      is Drawing.Translate -> TODO()
      is Drawing.Variable -> TODO()
      is Drawing.WithColor -> TODO()
      is Drawing.WithComposite -> TODO()
      is Drawing.WithFont -> TODO()
      is Drawing.Sprite -> Runnable {
        Game.background.canvas.draw(
          supplier(drawing.image).get(),
          supplier(drawing.x).invoke().toInt(),
          supplier(drawing.x).invoke().toInt()
        )

      }

      is Drawing.WithParallax -> TODO()
      is Drawing.WithText -> TODO()
    }

  fun supplier(integer: Integer): () -> Int =
    when (integer) {
      is Integer.Constant -> { -> integer.i }
      is Integer.Plus -> TODO()
      Integer.ScreenHeight -> { -> Game.WIDTH }
      Integer.ScreenWidth -> { -> Game.HEIGHT }
      is Integer.Variable -> TODO()
    }

  fun supplier(number: Number): () -> Double =
    when (number) {
      is Number.Animated -> TODO()
      is Number.Constant ->
        { -> number.d }
      is Number.Fraction ->
        supplier(number.a).let { a ->
          { a().let { it - floor(it) } }
        }
      Number.FrameSeconds ->
        { -> 1/60.0 }
      is Number.Negate ->
        supplier(number.a).let { a ->
          { -a() }
        }
      is Number.Plus ->
        supplier(number.a).let { a ->
          supplier(number.b).let { b ->
            { a() + b() }
          }
        }
      is Number.Times ->
        supplier(number.a).let { a ->
          supplier(number.b).let { b ->
            { a() * b() }
          }
        }
      is Number.Variable -> TODO()
      is Number.FromInteger ->
        supplier(number.i).let { i ->
          { i().toDouble() }
        }
    }
}

fun main() {
  val compiler = Compiler(DepressedChicken::class.java)
  val image = compiler.supplier(Image.Load("depressedChicken.png")).get()
  val drawing = compiler.runnable(
    Drawing.Sprite(
      Image.Load("depressedChicken.png"),
      Integer.Constant(100),
      Integer.Constant(100)
    )
  ).run()
  Game.start()
}