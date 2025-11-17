package greenhouse.entities;

//import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirTest {

  @Test
  public void updateStateShouldChangeHumidityLuxAndTemperatureEachUpdate() {
    Air airInGreenHouse = new Air(20, 0.5f, 10000);
    int humidityChanges = 0;
    int lightChanges = 0;
    int tempChanges = 0;

    for (int i = 0; i < 10; i++) {
      float preUpdateHumidity = airInGreenHouse.getHumidity();
      double preUpdateLight = airInGreenHouse.getLux();
      double preUpdateTemp = airInGreenHouse.getTemperature();
      airInGreenHouse.updateState();

      if (preUpdateHumidity != airInGreenHouse.getHumidity()) humidityChanges++;
      if (preUpdateLight != airInGreenHouse.getLux()) lightChanges++;
      if (preUpdateTemp != airInGreenHouse.getTemperature()) tempChanges++;
      airInGreenHouse.tick();
    }

    assertTrue(humidityChanges >= 10, "Humidity changed " + humidityChanges + "/10 times");
    assertTrue(lightChanges >= 10, "Light changed " + lightChanges + "/10 times");
    assertTrue(tempChanges >= 10, "Temperature changed " + tempChanges + "/10 times");
  }

  @Test
  public void stateValuesShouldConvergeTowardsTargetValues() {
    Air air = new Air(40, 0.9f, 10000);
    int convergedResultsTemp = 0;
    int convergedResultsHumidity = 0;
    for (int i = 0; i < 100; i++) {
      if (i > 40) {
        if (Math.abs(air.getTemperature() - air.getTargetTemperature()) < 3.0) {
          convergedResultsTemp++;
        }
        if (Math.abs(air.getHumidity() - air.getTargetHumidity()) < 0.10f) {
          convergedResultsHumidity++;
        }
      }
      air.updateState();

      System.out.println("Temp: " + air.getTemperature() + " | Target: " + air.getTargetTemperature()
              + " | Humidity: " + air.getHumidity() + " | Target: " + air.getTargetHumidity());
      air.tick();
    }
    System.out.println("Humidity: " + convergedResultsHumidity);
    System.out.println("Temp: " + convergedResultsTemp);

    assertTrue(convergedResultsHumidity >= 45,
            "Humidity converged " + convergedResultsHumidity + " times. >= 45 required.");

    assertTrue(convergedResultsTemp >= 45,
            "Temperature converged " + convergedResultsTemp + " times. >= 45 required.");
  }


}

