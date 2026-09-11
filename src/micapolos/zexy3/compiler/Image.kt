package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Image
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.zexy3.runtime.instantAnimation
import micapolos.tata8.Image as TataImage

fun Compiler.animatedImage(image: Image): Animated<TataImage?> =
  when (image) {
    Image.Empty -> Animated(ObjectEvaluator { null }, instantAnimation)

    is Image.Resource -> {
      val image = tataImages.computeIfAbsent(image.fileName) {
        Game.loadImage(baseClass.java, image.fileName)
      }
      Animated(
        ObjectEvaluator { image }, instantAnimation
      )
    }
  }
