package micapolos.zexy3

import micapolos.zexy3.model.Image as ModelImage
import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Value as ModelValue

class Image internal constructor(model: Any): Value<Image>(model)

internal val Value<Image>.modelImage get() = model as ModelValue<ModelImage>

val noImage = Image(ModelImage.Empty)

fun image(fileName: String) = Image(ModelImage.Resource(fileName))

val Value<Image>.width get() = Integer(ModelInteger.ImageWidth(modelImage))
val Value<Image>.height get() = Integer(ModelInteger.ImageHeight(modelImage))

fun Value<Image>.sprite(x: Int, y: Int) = sprite(this, x.value, y.value)
fun Value<Image>.sprite(x: Value<Integer>, y: Value<Integer>) = sprite(this, x, y)
