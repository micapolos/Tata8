package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Value as ModelValue

class Sprite internal constructor(model: Any) : Drawing<Sprite>(model)

internal val Value<Sprite>.modelSprite get() = model as ModelValue<ModelDrawing>
internal val ModelValue<ModelDrawing>.sprite: ModelDrawing.Sprite get() = this as ModelDrawing.Sprite

val sprite: Drawing<Sprite> = Sprite(ModelDrawing.Sprite(noImage.modelImage, 0.value.cast, 0.value.cast))

@JvmName("withImage")
fun Value<Sprite>.with(image: Value<Image>): Value<Sprite> =
  Sprite(ModelDrawing.Sprite(image.modelImage, modelSprite.sprite.x, modelSprite.sprite.y))

fun Value<Sprite>.withPosition(x: Int, y: Int): Value<Sprite> = withPosition(x.value, y.value)
fun Value<Sprite>.withPosition(x: Int, y: Value<Integer>): Value<Sprite> = withPosition(x.value, y)
fun Value<Sprite>.withPosition(x: Value<Integer>, y: Int): Value<Sprite> = withPosition(x, y.value)
fun Value<Sprite>.withPosition(x: Value<Integer>, y: Value<Integer>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.sprite.image,
      x.modelInteger,
      y.modelInteger))

@JvmName("withPosition")
fun Value<Sprite>.with(position: Value<Position>): Value<Sprite> =
  withPosition((position as Position).x, (position as Position).y)
