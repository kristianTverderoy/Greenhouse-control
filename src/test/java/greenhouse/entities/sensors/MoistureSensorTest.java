package greenhouse.entities.sensors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greenhouse.logic.Clock;
import greenhouse.logic.Soil;

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

  //Inconsistant
  @Test
  public void tickOnce() {
    Clock.getInstance().tick().run();
    assertTrue(sensor.getMoisture() < 50);
    assertTrue(sensor.getMoisture() >= 35);
  }
}
