package micapolos.ast

fun main() {
  val x = variable(10)
  val y = x + 50

  val animation = animation(
    x.keepAdding(1),
    fillRect(x, y, 30, 30),
    fillRect(screenWidth - x, screenHeight - 100, 10, 10))

  animation.writeJava("src/micapolos/Demko.java")
  animation.show()
}