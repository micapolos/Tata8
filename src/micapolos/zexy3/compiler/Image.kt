package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Image
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.noAnimation
import micapolos.tata8.Image as TataImage

fun Compiler.animatedImage(image: Image): Animated<TataImage?> =
  Animated(imageEvaluator(image), imageAnimation(image))

fun Compiler.imageAnimation(image: Image): Animation =
  when (image) {
    Image.Empty -> noAnimation
    is Image.Resource -> noAnimation
  }

fun Compiler.imageEvaluator(image: Image): ObjectEvaluator<TataImage?> =
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
  }