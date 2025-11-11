package greenhouse.entities;

//import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirTest {

  @Test
  public void stateChangeUpdate() {
    Air airInGreenHouse = new Air(20, 0.5f);
    int humidityChanges = 0;
    int lightChanges = 0;
    int tempChanges = 0;

    for (int i = 0; i < 10; i++) {
      float preUpdateHumidity = airInGreenHouse.getHumidity();
      double preUpdateLight = airInGreenHouse.getLight();
      double preUpdateTemp = airInGreenHouse.getTemperature();
      airInGreenHouse.updateState();
      System.out.println("Humidity: " + airInGreenHouse.getHumidity() + ", Light: " + airInGreenHouse.getLight() +
              ", Temp: " + airInGreenHouse.getTemperature());

      if (preUpdateHumidity != airInGreenHouse.getHumidity()) humidityChanges++;
      if (preUpdateLight != airInGreenHouse.getLight()) lightChanges++;
      if (preUpdateTemp != airInGreenHouse.getTemperature()) tempChanges++;
    }

    assertTrue(humidityChanges >= 8, "Humidity changed " + humidityChanges + "/10 times");
    assertTrue(lightChanges >= 8, "Light changed " + lightChanges + "/10 times");
    assertTrue(tempChanges >= 8, "Temperature changed " + tempChanges + "/10 times");
  }
}

