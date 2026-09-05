package micapolos.sad

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GeneratorTest {
  @Test
  fun test() {
    val generator = Generator()
    var x = newInt(int(0))
    assertEquals(
      """
import micapolos.tata8.Game;

fun main() {
  int v0 = 0;
  
  Game.onUpdate = () -> {
    int v0 = v1 + 1;
    v1 = v0;
    Game.background.canvas.fillRect(v1, v1, 123, 45);
    v1 = v0;
    Game.background.canvas.fillRect(v1, v1, 123, 45);
  };
}
      """.trim(),
      generator.generate(
        sequence(
          x.set(x.plus(int(1))),
          fillRect(x, x, int(123), int(45)))))
  }
}