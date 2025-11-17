package greenhouse.entities.sensors;

import greenhouse.entities.Clock;
import greenhouse.entities.Soil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    assertEquals(64, Math.round(sensor.getPh()*10));
  }
}
