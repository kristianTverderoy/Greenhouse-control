package greenhouse.entities;

//import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirTest {

  @Test
  public void updateStateShouldChangeHumidityLuxAndTemperatureEachUpdate() {
    Air airInGreenHouse = new Air(20, 0.5f);
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

    assertTrue(humidityChanges >= 10,  "Humidity changed " + humidityChanges + "/10 times");
    assertTrue(lightChanges >= 10, "Light changed " + lightChanges + "/10 times");
    assertTrue(tempChanges >= 10, "Temperature changed " + tempChanges + "/10 times");
  }
}

