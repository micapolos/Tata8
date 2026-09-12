package micapolos.zexy

import micapolos.zexy.model.Image as ModelImage
import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Value as ModelValue

class Image internal constructor(model: Any): Value<Image>(model)

internal val Value<Image>.modelImage get() = model as ModelValue<ModelImage>

val noImage = Image(ModelImage.Empty)

fun image(fileName: String) = Image(ModelImage.Resource(fileName))

val Value<Image>.width get() = Integer(ModelInteger.ImageWidth(modelImage))
val Value<Image>.height get() = Integer(ModelInteger.ImageHeight(modelImage))

fun Value<Image>.show() {
  sprite.with(this).show()
}