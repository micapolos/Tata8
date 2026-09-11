package micapolos.zexy3

import micapolos.zexy3.model.Drawing as ModelDrawing
import micapolos.zexy3.model.Value as ModelValue

class Sprite internal constructor(model: Any) : Drawing<Sprite>(model)

internal val Value<Sprite>.modelSprite get() = model as ModelValue<ModelDrawing>
internal val ModelValue<ModelDrawing>.sprite: ModelDrawing.Sprite get() = this as ModelDrawing.Sprite

val Value<Sprite>.x get() = Integer(modelSprite.sprite.x)
val Value<Sprite>.y get() = Integer(modelSprite.sprite.y)
val Value<Sprite>.width get() = Integer(modelSprite.sprite.width)
val Value<Sprite>.height get() = Integer(modelSprite.sprite.height)
val Value<Sprite>.image get() = Image(modelSprite.sprite.image)
val Value<Sprite>.imageX get() = Integer(modelSprite.sprite.imageX)
val Value<Sprite>.imageY get() = Integer(modelSprite.sprite.imageY)

val sprite: Drawing<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      0.value.cast, 0.value.cast,
      0.value.cast, 0.value.cast,
      noImage.modelImage,
      0.value.cast, 0.value.cast))

@JvmName("withImage")
fun Value<Sprite>.with(image: Value<Image>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.sprite.x, modelSprite.sprite.y,
      image.width.modelInteger, image.height.modelInteger,
      image.modelImage,
      modelSprite.sprite.imageX, modelSprite.sprite.imageY))

fun Value<Sprite>.withPosition(x: Int, y: Int): Value<Sprite> = withPosition(x.value, y.value)
fun Value<Sprite>.withPosition(x: Int, y: Value<Integer>): Value<Sprite> = withPosition(x.value, y)
fun Value<Sprite>.withPosition(x: Value<Integer>, y: Int): Value<Sprite> = withPosition(x, y.value)
fun Value<Sprite>.withPosition(x: Value<Integer>, y: Value<Integer>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      x.modelInteger, y.modelInteger,
      modelSprite.sprite.width, modelSprite.sprite.height,
      modelSprite.sprite.image,
      modelSprite.sprite.imageX, modelSprite.sprite.imageY))

fun Value<Sprite>.withSize(width: Int, height: Int): Value<Sprite> = withSize(width.value, height.value)
fun Value<Sprite>.withSize(width: Int, height: Value<Integer>): Value<Sprite> = withSize(width.value, height)
fun Value<Sprite>.withSize(width: Value<Integer>, height: Int): Value<Sprite> = withSize(width, height.value)
fun Value<Sprite>.withSize(width: Value<Integer>, height: Value<Integer>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.sprite.x, modelSprite.sprite.y,
      width.modelInteger, height.modelInteger,
      modelSprite.sprite.image,
      modelSprite.sprite.imageX, modelSprite.sprite.imageY))

fun Value<Sprite>.withImagePosition(x: Int, y: Int): Value<Sprite> = withImagePosition(x.value, y.value)
fun Value<Sprite>.withImagePosition(x: Int, y: Value<Integer>): Value<Sprite> = withImagePosition(x.value, y)
fun Value<Sprite>.withImagePosition(x: Value<Integer>, y: Int): Value<Sprite> = withImagePosition(x, y.value)
fun Value<Sprite>.withImagePosition(x: Value<Integer>, y: Value<Integer>): Value<Sprite> =
  Sprite(
    ModelDrawing.Sprite(
      modelSprite.sprite.x, modelSprite.sprite.y,
      modelSprite.sprite.width, modelSprite.sprite.height,
      modelSprite.sprite.image,
      x.modelInteger, y.modelInteger))

@JvmName("withPosition")
fun Value<Sprite>.with(position: Value<Position>): Value<Sprite> =
  withPosition((position as Position).x, (position as Position).y)

@JvmName("withSize")
fun Value<Sprite>.with(size: Value<Size>): Value<Sprite> =
  withSize((size as Size).width, (size as Size).height)

@JvmName("withImagePosition")
fun Value<Sprite>.withImage(position: Value<Position>): Value<Sprite> =
  withImagePosition((position as Position).x, (position as Position).y)

