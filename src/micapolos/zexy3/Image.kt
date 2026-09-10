package micapolos.zexy3

import micapolos.zexy3.model.Image as ModelImage
import micapolos.zexy3.model.Integer as ModelInteger

class Image internal constructor(model: ModelImage): Value<Image>(model)

internal val Value<Image>.modelImage get() = model as ModelImage

fun image(fileName: String) = Image(ModelImage.Resource(fileName))

val Value<Image>.width get() = Integer(ModelInteger.ImageWidth(modelImage))
val Value<Image>.height get() = Integer(ModelInteger.ImageHeight(modelImage))
