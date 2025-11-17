package greenhouse.entities.sensors;

import greenhouse.entities.Clock;
import greenhouse.entities.Soil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoistureSensorTest {
  private Soil soil;
  private MoistureSensor sensor;

  @BeforeEach
  public void init() {
    soil = new Soil(50, 7, 20);
    sensor = new MoistureSensor<>(1, soil);
    sensor.subscribe(soil);
  }

  @Test
  public void tickOnce() {
    Clock.getInstance().tick().run();
    assertTrue(sensor.getMoisture() < 50);
    assertTrue(sensor.getMoisture() >= 45);
  }
}
