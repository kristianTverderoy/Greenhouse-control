package greenhouse.entities;

import greenhouse.entities.sensors.NitrogenSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreenHouseTest {
  private GreenHouse greenHouse;

  @BeforeEach
  public void init() {
    greenHouse = new GreenHouse(1);
    greenHouse.addNitrogenSensor();
  }

  @Test
  public void tickOnce() {
    Clock.getInstance().tick().run();
    assertEquals(17, ((NitrogenSensor<?>) greenHouse.getSensor(0)).getNitrogen());
  }
}
