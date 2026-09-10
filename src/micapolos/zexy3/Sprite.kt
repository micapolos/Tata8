package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing

class Sprite internal constructor(model: Any) : Drawing<Sprite>(model)

internal val Value<Sprite>.modelSprite get() = model as ModelDrawing.Sprite

val sprite: Drawing<Sprite> = Sprite(ModelDrawing.Sprite(noImage.modelImage, 0.value.cast, 0.value.cast))

@JvmName("withImage")
fun Drawing<Sprite>.with(image: Value<Image>): Drawing<Sprite> =
  Sprite(ModelDrawing.Sprite(image.modelImage, modelSprite.x, modelSprite.y))

@JvmName("withPosition")
fun Drawing<Sprite>.with(positionValue: Value<Position>): Drawing<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.image,
      positionValue.position.x.cast,
      positionValue.position.y.cast))