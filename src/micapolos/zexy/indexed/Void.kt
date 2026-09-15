package micapolos.zexy.indexed

sealed class Void : Value<Void> {
  object Empty : Void()
}