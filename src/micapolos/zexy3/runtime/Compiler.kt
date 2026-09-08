package micapolos.zexy3.runtime

import micapolos.DepressedChicken
import micapolos.tata8.Game
import micapolos.zexy3.*
import micapolos.zexy3.Number
import micapolos.zexy3.dsl.*
import kotlin.math.floor

val Key.tata
  get() =
    when (this) {
      Key.LEFT -> Game.keys.left
      Key.RIGHT -> Game.keys.right
      Key.UP -> Game.keys.up
      Key.DOWN -> Game.keys.down
      Key.Z -> Game.keys.z
      Key.X -> Game.keys.x
    }

class Compiler(val baseClass: Class<*>) {
  val loadedImages: MutableMap<String, micapolos.tata8.Image> = mutableMapOf()
  val numberBoxes = mutableMapOf<Number.Variable, Box<Double>>()
  val inits = mutableListOf<() -> Unit>()
  val updates = mutableListOf<() -> Unit>()

  fun runner(animation: Animation): Runner =
    when (animation) {
      Animation.Instant -> instantRunner
      Animation.Forever -> foreverRunner
      is Animation.Parallel -> TODO()
      is Animation.Pause -> TODO()
      is Animation.RunWhile -> TODO()
      is Animation.Select -> TODO()
      is Animation.Sequence -> TODO()
      is Animation.StartOn -> TODO()
      is Animation.StopOn -> TODO()
      is Animation.Stretch -> TODO()
      is Animation.WithAction -> startRunner(runnable(animation.action))
    }

  fun supplier(image: Image): () -> micapolos.tata8.Image =
    when (image) {
      is Image.Create -> TODO()
      is Image.Load -> {
        val image = loadedImages.getOrPut(image::class.java.name) {
          IO.println("Loading image: ${image.fileName}")
          Game.loadImage(baseClass, image.fileName)
        }
        return { image }
      }

      is Image.Slice -> TODO()
      is Image.Variable -> TODO()
    }

  fun runnable(drawing: Drawing): () -> Unit =
    when (drawing) {
      is Drawing.Rotate -> TODO()
      is Drawing.Scale -> TODO()
      is Drawing.Select -> TODO()
      is Drawing.Stack -> {
        val drawings = drawing.drawings.map { runnable(it) }
        return { drawings.forEach { it() } }
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
      is Drawing.Label -> { ->
        Game.background.canvas.draw(
          supplier(drawing.text).invoke(),
          supplier(drawing.x).invoke().toInt(),
          supplier(drawing.y).invoke().toInt(),
          supplier(drawing.color).invoke(),
          supplier(drawing.font).invoke(),
          supplier(drawing.shadow).invoke(),
        )
      }
    }

  fun supplier(bool: Bool): () -> Boolean =
    when (bool) {
      is Bool.Constant -> { -> bool.b }
      is Bool.And -> TODO()
      is Bool.IndexEqual -> TODO()
      is Bool.KeyPressed -> {
        val key = bool.key.tata
        return { key.isPressed }
      }

      Bool.MousePressed -> TODO()
      is Bool.Not -> TODO()
      is Bool.NumberEqual -> TODO()
      is Bool.Or -> TODO()
      is Bool.Variable -> TODO()
    }

  fun supplier(integer: Integer): () -> Int =
    when (integer) {
      is Integer.Constant -> { -> integer.i }
      is Integer.Plus -> TODO()
      Integer.ScreenHeight -> { -> Game.WIDTH }
      Integer.ScreenWidth -> { -> Game.HEIGHT }
      is Integer.Variable -> TODO()
    }

  fun box(number: Number): Box<Double> {
    val variable = number as Number.Variable
    val initial = supplier(variable.initial)
    return numberBoxes.computeIfAbsent(variable) {
      val box = Box(defaultValue = 0.0)
      inits.add({ box.supplier = initial })
      box
    }
  }

  fun supplier(text: Text): () -> String =
    when (text) {
      is Text.Constant -> { -> text.string }
      is Text.Variable -> TODO()
    }

  fun supplier(color: Color): () -> micapolos.tata8.Color =
    when (color) {
      Color.Black -> { -> micapolos.tata8.Color.BLACK }
      Color.Yellow -> { -> micapolos.tata8.Color.YELLOW }
      is Color.Variable -> TODO()
    }

  fun supplier(font: Font): () -> micapolos.tata8.Font =
    when (font) {
      is Font.Load -> TODO()
      is Font.Variable -> TODO()
    }

  fun supplier(number: Number): () -> Double =
    when (number) {
      is Number.Animated -> TODO()
      is Number.Constant -> { -> number.d }
      is Number.Fraction -> {
        val a = supplier(number.a)
        return { a().let { it - floor(it) } }
      }

      Number.FrameSeconds -> { -> 1 / 60.0 }
      is Number.Negate -> {
        val a = supplier(number.a)
        return { -a() }
      }

      is Number.Plus -> {
        val a = supplier(number.a)
        val b = supplier(number.b)
        return { a() + b() }
      }

      is Number.Minus -> {
        val a = supplier(number.a)
        val b = supplier(number.b)
        return { a() - b() }
      }

      is Number.Times -> {
        val a = supplier(number.a)
        val b = supplier(number.b)
        return { a() * b() }
      }

      is Number.Variable -> {
        val box = box(number)
        return { box.value }
      }

      is Number.FromInteger -> {
        val i = supplier(number.i)
        return { i().toDouble() }
      }

      is Number.Conditional -> {
        val condition = supplier(number.condition)
        val trueNumber = supplier(number.trueNumber)
        val falseNumber = supplier(number.falseNumber)
        return { if (condition()) trueNumber() else falseNumber() }
      }

      is Number.Logged -> {
        val label = number.label
        val number = supplier(number.number)
        return {
          val double = number()
          if (label != null) Game.log(label, double) else Game.log(double)
          double
        }
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
  val x = newVariable(0.0)
  val box = compiler.box(x)
  val drawing = compiler.runnable(
    sprite(
      image("depressedChicken.png"),
      position(x.logged + (Screen.width.number - 32.0) * 0.5, 10.0)
    )
  )
  compiler.updates.add(drawing)
  compiler.updates.add(compiler.runnable(x.capture(x + Key.LEFT.isPressed.ifTrue(-1.0).orElse(0.0))))
  compiler.updates.add(compiler.runnable(x.capture(x + Key.RIGHT.isPressed.ifTrue(1.0).orElse(0.0))))
  compiler.inits.forEach { it() }
  IO.println(box.value)
  Game.onUpdate = {
    Game.background.canvas.clear()
    compiler.updates.forEach { it() }
  }
  Game.start()
}