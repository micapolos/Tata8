package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Image
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation
import micapolos.tata8.Image as TataImage

fun Compiler.animation(image: Image): Animation =
  when (image) {
    Image.Empty -> noAnimation
    is Image.Render -> noAnimation
    is Image.Resource -> noAnimation
    is Image.Slice -> noAnimation
  }

fun Compiler.evaluator(image: Image): ObjectEvaluator<TataImage?> =
  when (image) {
    Image.Empty -> {
      ObjectEvaluator { null }
    }
    is Image.Resource -> {
      val image = tataImages.computeIfAbsent(image.fileName) {
        Game.loadImage(baseClass.java, image.fileName)
      }
      ObjectEvaluator { image }
    }
    is Image.Render -> TODO()
    is Image.Slice -> TODO()
  }