package micapolos.sad

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GeneratorTest {
  @Test
  fun test() {
    val generator = Generator()
    val x = newInt()
    val y = newInt()
    assertEquals(
      """
import micapolos.tata8.Game;

fun main() {
  int v2 = 0;
  int v7 = 0;
  
  Game.onUpdate = () -> {
    int v4 = v2 + 1;
    v2 = v4;
    int v9 = v7 + 2;
    v7 = v9;
    Game.background.canvas.fillRect(v2, v7, 100, 200);
    v2 = v4;
    v7 = v9;
    Game.background.canvas.fillRect(v2, v7, 100, 200);
  };
}
      """.trimIndent(),
      generator.generate(
        sequence(
          x.set(x.plus(int(1))),
          y.set(y.plus(int(2))),
          fillRect(x, y, int(100), int(200)))))
  }
}