package greenhouse.entities;

import greenhouse.entities.appliances.Fertilizer;
import greenhouse.entities.appliances.Limer;
import greenhouse.entities.sensors.NitrogenSensor;
import greenhouse.entities.sensors.PHSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GreenHouseTest {
  private GreenHouse greenHouse;

  @BeforeEach
  public void init() {
    greenHouse = new GreenHouse(1);
  }

  @Test
  public void tickOnce() {
    greenHouse.addNitrogenSensor();
    Clock.getInstance().tick().run();
    assertEquals(17, ((NitrogenSensor<?>) greenHouse.getSensor(0)).getNitrogen());
  }

  @Test
  public void actuateFertilizer() {
    greenHouse.addAppliance(new Fertilizer(greenHouse.getNextAvailableApplianceId()));
    greenHouse.addNitrogenSensor();
    greenHouse.actuateAppliance(0);
    assertEquals(27, ((NitrogenSensor<?>) greenHouse.getSensor(0)).getNitrogen());
  }
}
