package micapolos.zexy2.live

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RunnerTest {
  @Test
  fun testPauseRunner() {
    var pause: () -> Float = { 10f }
    val runner = pauseRunner { pause() }
    runner.init()

    pause = { error("") }
    assertEquals(0f, runner.step(1f))
    assertEquals(0f, runner.step(8f))
    assertEquals(2f, runner.step(3f))

    pause = { 1f }
    runner.init()

    pause = { error("") }
    assertEquals(9f, runner.step(10f))
  }

  @Test
  fun testParallelRunner() {
    var pause1: () -> Float = { 10f }
    var pause2: () -> Float = { 5f }
    val runner = parallel(pauseRunner { pause1() }, pauseRunner { pause2() })
    runner.init()

    pause1 = { error("") }
    pause2 = { error("") }
    assertEquals(0f, runner.step(3f))
    assertEquals(0f, runner.step(5f))
    assertEquals(3f, runner.step(5f))
    assertEquals(5f, runner.step(5f))

    pause1 = { 1f }
    pause2 = { 1f }
    runner.init()
    assertEquals(4f, runner.step(5f))
  }

  @Test
  fun testSequenceRunner() {
    var pause1: () -> Float  = { error("") }
    var pause2: () -> Float  = { error("") }
    val runner = sequence(pauseRunner { pause1() }, pauseRunner { pause2() })
    runner.init()

    pause1 = { 10f }
    assertEquals(0f, runner.step(9f))

    runner.init()
    assertEquals(0f, runner.step(10f))

    runner.init()
    assertThrows(IllegalStateException::class.java) { runner.step(11f) }

    pause2 = { 5f }

    runner.init()
    assertEquals(0f, runner.step(13f))

    runner.init()
    assertEquals(5f, runner.step(20f))
  }
}