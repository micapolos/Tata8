package micapolos.zexy3

data class Game(
  val title: String,
  val width: Int,
  val height: Int,
  val drawing: Value<Drawing>,
)