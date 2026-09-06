package micapolos.zexy2.ast

interface Runner {
  fun init() {}
  fun step(seconds: Float) = seconds
}