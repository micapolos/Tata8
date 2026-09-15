package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Image
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.ObjectEvaluator
import micapolos.tata8.Image as TataImage

fun Compiler.imageEvaluator(image: Image): Evaluator<TataImage?> =
  when (image) {
    Image.Empty -> ObjectEvaluator { null }

    is Image.Resource -> {
      val image = tataImages.computeIfAbsent(image.fileName) {
        Game.loadImage(baseClass.java, image.fileName)
      }
      ObjectEvaluator { image }
    }
  }
