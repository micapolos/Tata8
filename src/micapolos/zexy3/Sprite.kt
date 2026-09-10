package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing

class Sprite internal constructor(model: Any) : Value<Sprite>(model)

internal val Value<Sprite>.modelSprite get() = model as ModelDrawing.Sprite

val sprite = Sprite(ModelDrawing.Sprite(noImage.modelImage, 0.value.cast, 0.value.cast))

@JvmName("withImage")
fun Value<Sprite>.with(image: Value<Image>): Value<Sprite> =
  Sprite(ModelDrawing.Sprite(noImage.modelImage, modelSprite.x, modelSprite.y))

@JvmName("withPosition")
fun Value<Sprite>.with(positionValue: Value<Position>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.image,
      positionValue.position.x.cast,
      positionValue.position.y.cast))