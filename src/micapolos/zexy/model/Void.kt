package micapolos.zexy.model

sealed class Void: Value<Void> {
  data object Empty: Void()
}