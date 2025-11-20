package greenhouse.entities;

import greenhouse.entities.appliances.*;
import greenhouse.entities.sensors.MoistureSensor;
import greenhouse.entities.sensors.NitrogenSensor;
import greenhouse.entities.sensors.PHSensor;
import greenhouse.logic.Clock;
import greenhouse.logic.GreenHouse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GreenHouseTest {
  private GreenHouse greenHouse;

  /**
   * Constructs the instance of the greenhouse before each test.
   */
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

  @Test
  public void actuateSprinkler() {
    greenHouse.addAppliance(new Sprinkler(greenHouse.getNextAvailableApplianceId()));
    greenHouse.addMoistureSensor();
    greenHouse.actuateAppliance(0);
    assertTrue(((MoistureSensor<?>)greenHouse.getSensor(0)).getMoisture() > 60);
  }

  @Test
  public void actuateLimer() {
    greenHouse.addAppliance(new Limer(greenHouse.getNextAvailableApplianceId()));
    greenHouse.addPhSensor();
    double oldPh = ((PHSensor<?>) greenHouse.getSensor(0)).getPh();
    greenHouse.actuateAppliance(0);
    assertEquals(oldPh + 0.5, ((PHSensor<?>) greenHouse.getSensor(0)).getPh());
  }

  @Test
  public void turnOnAircondition() {
    greenHouse.addAppliance(new Aircondition(greenHouse.getNextAvailableApplianceId()));
    greenHouse.actuateAppliance(0);
    assertTrue(((Aircondition) greenHouse.getAppliance(0)).getPowerState());
  }

  @Test
  public void turnOnLamp() {
    greenHouse.addAppliance(new Lamp(greenHouse.getNextAvailableApplianceId()));
    greenHouse.actuateAppliance(0);
    assertTrue(((Lamp) greenHouse.getAppliance(0)).getPowerState());
  }

  @Test
  public void turnOnHumidifier() {
    greenHouse.addAppliance(new Humidifier(greenHouse.getNextAvailableApplianceId()));
    greenHouse.actuateAppliance(0);
    assertTrue(((Humidifier) greenHouse.getAppliance(0)).getPowerState());
  }

}
