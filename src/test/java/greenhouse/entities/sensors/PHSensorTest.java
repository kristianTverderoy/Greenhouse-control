package greenhouse.entities.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greenhouse.logic.Clock;
import greenhouse.logic.Soil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PHSensorTest {
  private Soil soil;
  private PHSensor sensor;

  @BeforeEach
  public void init() {
    soil = new Soil(50, 7, 20);
    sensor = new PHSensor<>(1, soil);
    sensor.subscribe(soil);
  }

  @Test
  public void tickOnce() {
    Clock.getInstance().tick().run();
    assertEquals(61, Math.round(sensor.getPh()*10));
  }
}
