package greenhouse.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClockTest {
  Clock clock = Clock.getInstance();

  @BeforeEach
  public void init() {
    clock.start();
  }

  //------------------------------- POSITIVE TESTS ----------------------------------

  @Test
  public void stopRunning() {
    clock.stop();
  }


  @Test
  public void speedUpOnce() {
    clock.speedUp(1);
    assertEquals(5, clock.getCurrentRate());
  }

  @Test
  public void speedUpTwice() {
    clock.speedUp(2);
    assertEquals(1, clock.getCurrentRate());
  }

  @Test
  public void speedUpTrice() {
    clock.speedUp(2);
    clock.speedUp(1);
    assertEquals(1, clock.getCurrentRate());
  }

  @Test
  public void speedUpAndSlowDown() {
    clock.speedUp(1);
    clock.slowDown(1);
    assertEquals(10, clock.getCurrentRate());
  }

  @Test
  public void slowDownMoreThanSpeedUp() {
    clock.speedUp(1);
    clock.slowDown(2);
    assertEquals(10, clock.getCurrentRate());
  }

  //--------------------------------- NEGATIVE TESTS ------------------------------------

  @Test
  public void speedUpOverLimit() {
    try {
      clock.speedUp(3);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

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
