package micapolos.leo

data class Sourced<out T>(
  val source: Source,
  val value: T,
)
