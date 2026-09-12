package micapolos.zexy.model

data class Game(
  val title: String,
  val width: Int,
  val height: Int,
  val drawing: Value<Drawing>,
)