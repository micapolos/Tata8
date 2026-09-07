package micapolos.zexy2.live

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AnimationTest {
  @Test
  fun testPauseRunner() {
    var pauseState = State<Double>(10.0)
    val runner = sleepRunner(pauseState)
    runner.init()

    pauseState.internalValue = null
    assertEquals(0f, runner.step(1f))
    assertEquals(0f, runner.step(8f))
    assertEquals(2f, runner.step(3f))

    pauseState.value = 1.0
    runner.init()

    pauseState.internalValue = null
    assertEquals(9f, runner.step(10f))
  }

  @Test
  fun testParallelRunner() {
    var pause1 = State<Double>(10.0)
    var pause2 = State<Double>(10.0)
    val runner = parallel(sleepRunner(pause1), sleepRunner(pause2))
    runner.init()

    pause1.internalValue = null
    pause2.internalValue = null
    assertEquals(0f, runner.step(3f))
    assertEquals(0f, runner.step(5f))
    assertEquals(3f, runner.step(5f))
    assertEquals(5f, runner.step(5f))

    pause1.value = 1.0
    pause2.value = 1.0
    runner.init()
    assertEquals(4f, runner.step(5f))
  }

  @Test
  fun testSequenceRunner() {
    var pause1 = State<Double>(null)
    var pause2 = State<Double>(null)
    val runner = sequence(sleepRunner(pause1), sleepRunner(pause2))
    runner.init()

    pause1.value = 10.0
    assertEquals(0f, runner.step(9f))

    runner.init()
    assertEquals(0f, runner.step(10f))

    runner.init()
    assertThrows(NullPointerException::class.java) { runner.step(11f) }

    pause2.value = 5.0

    runner.init()
    assertEquals(0f, runner.step(13f))

    runner.init()
    assertEquals(5f, runner.step(20f))
  }
}