package micapolos.zexy

import micapolos.zexy.model.Image as ModelImage
import micapolos.zexy.model.Integer as ModelInteger
import micapolos.zexy.model.Value as ModelValue

class Image internal constructor(model: ModelValue<*>): ValueWithModel<Image>(model)

internal val Value<Image>.modelImage get() = model as ModelValue<ModelImage>

val noImage = Image(ModelImage.Empty)

fun image(fileName: String) = Image(ModelImage.Resource(fileName))

internal val Value<Image>.width get() = Integer(ModelInteger.ImageWidth(modelImage))
internal val Value<Image>.height get() = Integer(ModelInteger.ImageHeight(modelImage))
val Value<Image>.size get() = size(width, height)

fun Value<Image>.show() {
  sprite.with(this).show()
}