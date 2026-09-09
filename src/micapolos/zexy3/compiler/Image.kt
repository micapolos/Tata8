package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.Image
import micapolos.zexy3.runtime.ObjectEvaluator
import micapolos.tata8.Image as TataImage

fun Compiler.compile(image: Image): ObjectEvaluator<TataImage> =
  when (image) {
    is Image.Resource -> {
      val image = tataImages.computeIfAbsent(image.fileName) {
        Game.loadImage(baseClass.java, image.fileName)
      }
      ObjectEvaluator { image }
    }
    is Image.Render -> TODO()
    is Image.Slice -> TODO()
  }