package micapolos.zexy.indexed

class Game(
  val title: String,
  val width: Int,
  val height: Int,
  @Deprecated("replaced with animation")
  val drawing: Value<Drawing>,
  val animation: Animation,
)