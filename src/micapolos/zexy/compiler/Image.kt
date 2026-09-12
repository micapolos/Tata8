package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Image
import micapolos.zexy.runtime.Animated
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.zexy.runtime.instantAnimation
import micapolos.tata8.Image as TataImage

fun Compiler.animatedImage(image: Image): Animated<TataImage?> =
  when (image) {
    Image.Empty -> Animated(ObjectEvaluator { null }, instantAnimation)

    is Image.Resource -> {
      val image = tataImages.computeIfAbsent(image.fileName) {
        Game.loadImage(baseClass.java, image.fileName)
      }
      Animated(ObjectEvaluator { image }, instantAnimation)
    }
  }
