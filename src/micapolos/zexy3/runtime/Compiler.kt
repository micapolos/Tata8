package micapolos.zexy3.runtime

import micapolos.DepressedChicken
import micapolos.tata8.Game
import micapolos.zexy3.Action
import micapolos.zexy3.Drawing
import micapolos.zexy3.Image
import micapolos.zexy3.Integer
import micapolos.zexy3.Number
import java.util.function.Supplier
import kotlin.math.floor

class Compiler(val baseClass: Class<*>) {
  val loadedImages: MutableMap<String, micapolos.tata8.Image> = mutableMapOf()
  val numberBoxes = mutableMapOf<Number.Variable, Box<Double>>()
  val inits = mutableListOf<() -> Unit>()
  val updates = mutableListOf<() -> Unit>()

  fun supplier(image: Image): () -> micapolos.tata8.Image =
    when (image) {
      is Image.Create -> TODO()
      is Image.Load ->
        loadedImages.getOrPut(image::class.java.name) {
          IO.println("Loading image: ${image.fileName}")
          Game.loadImage(baseClass, image.fileName)
        }.let { image ->
          { image }
        }

      is Image.Slice -> TODO()
      is Image.Variable -> TODO()
    }

  fun runnable(drawing: Drawing): () -> Unit =
    when (drawing) {
      is Drawing.Rotate -> TODO()
      is Drawing.Scale -> TODO()
      is Drawing.Select -> TODO()
      is Drawing.Stack -> drawing.drawings.map { runnable(it) }.let { runnables ->
        { -> runnables.forEach { it() } }
      }
      is Drawing.Translate -> TODO()
      is Drawing.Variable -> TODO()
      is Drawing.WithColor -> TODO()
      is Drawing.WithComposite -> TODO()
      is Drawing.WithFont -> TODO()
      is Drawing.Sprite -> { ->
        Game.background.canvas.draw(
          supplier(drawing.image).invoke(),
          supplier(drawing.x).invoke().toInt(),
          supplier(drawing.y).invoke().toInt()
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

  fun box(number: Number): Box<Double> =
    (number as Number.Variable).let { variable ->
      supplier(variable.initial).let { initial ->
        numberBoxes.computeIfAbsent(variable) {
          Box(defaultValue = 0.0).also { box ->
            inits.add({ box.supplier = initial })
          }
        }
      }
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
      is Number.Minus ->
        supplier(number.a).let { a ->
          supplier(number.b).let { b ->
            { a() - b() }
          }
        }
      is Number.Times ->
        supplier(number.a).let { a ->
          supplier(number.b).let { b ->
            { a() * b() }
          }
        }
      is Number.Variable ->
        box(number).let { box ->
          { box.value }
        }
      is Number.FromInteger ->
        supplier(number.i).let { i ->
          { i().toDouble() }
        }
    }

  fun runnable(action: Action): () -> Unit =
    when (action) {
      is Action.BoolSet -> TODO()
      is Action.DrawingSet -> TODO()
      is Action.FontSet -> TODO()
      is Action.ImageSet -> TODO()
      is Action.IntegerSet -> TODO()
      is Action.NumberSet -> {
        val box = box(action.variable)
        val value = supplier(action.value)
        return { box.supplier = value }
      }
      is Action.NumberCapture -> {
        val box = box(action.variable)
        val value = supplier(action.value)
        return { box.value = value() }
      }
      is Action.TextSet -> TODO()
    }
}

fun main() {
  val compiler = Compiler(DepressedChicken::class.java)
  val x = newVariable(100.0)
  val box = compiler.box(x)
  val drawing = compiler.runnable(
    sprite(
      image("depressedChicken.png"),
      position(x + (Screen.width.number - 32.0) * 0.5, 10.0)))
  compiler.updates.add(drawing)
  compiler.updates.add(compiler.runnable(x.capture(x + 1.0)))
  compiler.inits.forEach { it() }
  IO.println(box.value)
  Game.onUpdate = {
    Game.background.canvas.clear()
    compiler.updates.forEach { it() }
  }
  Game.start()
}