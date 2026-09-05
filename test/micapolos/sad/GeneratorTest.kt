package micapolos.sad

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GeneratorTest {
  @Test
  fun test() {
    val generator = Generator()
    val x = newInt(16)
    val y = newInt(32)
    val z = int(12)
    assertEquals(
      """
import micapolos.tata8.Game;

fun main() {
  int v1 = 16;
  int v3 = 32;
  Game.onUpdate = () -> {
    int v2 = v1 + 1;
    v1 = v2;
    int v4 = v3 + 2;
    v3 = v4;
    Game.background.canvas.fillRect(v1, v3, 100, 200);
  };
  Game.start();
}
      """.trimIndent(),
      generator.generate(
        sequence(
          x.set(x.plus(int(1))),
          y.set(y.plus(int(2))),
          fillRect(x, y, int(100), int(200)))))
  }
}