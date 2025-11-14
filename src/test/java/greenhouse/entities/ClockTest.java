package greenhouse.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Clock class.
 */
public class ClockTest {
  Clock clock = Clock.getInstance();

  /**
   * Resets and starts the clock before each test.
   */
  @BeforeEach
  public void init() {
    clock.start();
  }

  //------------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Tests that the clock is able to stop and start without issue.
   */
  @Test
  public void stopRunning() {
    clock.stop();
  }

  /**
   * Tests if the clock is able to speed up from NORMAL speed to MODERATE speed.
   */
  @Test
  public void speedUpOnce() {
    clock.speedUp(1);
    assertEquals(5, clock.getCurrentRate());
  }

/**
 * Tests if the clock is able to speed up from NORMAL speed to FAST speed.
 */
  @Test
  public void speedUpTwice() {
    clock.speedUp(2);
    assertEquals(1, clock.getCurrentRate());
  }

  /**
   * Checks that the clock speed remains ant FAST if the clock tries to speed up
   * more when it already is FAST.
   */
  @Test
  public void speedUpTrice() {
    clock.speedUp(2);
    clock.speedUp(1);
    assertEquals(1, clock.getCurrentRate());
  }

  /**
   * Checks that the clock is able to slow down by speeding it
   * up once then slowing it down once.
   */
  @Test
  public void speedUpAndSlowDown() {
    clock.speedUp(1);
    clock.slowDown(1);
    assertEquals(10, clock.getCurrentRate());
  }

  /**
   * Checks that the speed remains NORMAL if the clock is sped
   * down while it has speed NORMAL.
   */
  @Test
  public void slowDownWhileNormalSpeed() {
    clock.slowDown(1);
    assertEquals(10, clock.getCurrentRate());
  }

  //--------------------------------- NEGATIVE TESTS ------------------------------------

  /**
   * Checks that the clock throws an exception if it tries to speed
   * up with a jump longer than 2.
   */
  @Test
  public void speedUpOverLimit() {
    try {
      clock.speedUp(3);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Checks that the clock throws an exception
   */
  @Test
  public void slowDownOverLimit() {
    try {
      clock.slowDown(3);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void speedUpUnderLimit() {
    try {
      clock.speedUp(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void slowDownUnderLimit() {
    try {
      clock.slowDown(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }
}
