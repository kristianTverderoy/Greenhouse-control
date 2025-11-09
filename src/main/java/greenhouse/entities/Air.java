package greenhouse.entities;

public class Air implements Sensorable {

  private float humidity; // 0 <= x <= 1
  private double light;
  private double temperature;
  private float humidityTarget;
  private double tempTarget;
  private boolean initialTempSet = false;
  private boolean initialLightSet = false;
  private boolean initialHumiditySet = false;


  public Air(double tempTarget, float humidityTarget) {
    this.humidityTarget = humidityTarget;
    this.tempTarget = tempTarget;
  }

  /**
   * Interface method, to make all Sensorable objects update their state values.
   */
  @Override
  public void updateState() {
    // humidity: 0.0 - 1.0
    if (humidity == 0.0f && !initialHumiditySet) {
      humidity = (float) (Math.random());
      initialHumiditySet = true;
    }
    // light (lux): 0.0 - 100000.0
    if (light == 0.0 && !initialLightSet) {
      light = (double) (Math.random() * 100000);
      initialLightSet = true;
    }
    // temperature (°C): 0.0 - 40.0
    if (temperature == 0.0 && !initialTempSet) {
      temperature = (double) (Math.random() * 40);
      initialTempSet = true;
    }

    tempUpProbability();
  }

  /**
   * Simulates temperature change probability based on the difference
   * between current temperature and target temperature.
   */
  private void tempUpProbability() {
    float tempIncreaseChance;
    if (Math.abs(temperature - tempTarget) > 20) {
      tempIncreaseChance = 0.9f;
    } else if (Math.abs(temperature - tempTarget) > 10) {
      tempIncreaseChance = 0.7f;
    } else if (Math.abs(temperature - tempTarget) > 2) {
      tempIncreaseChance = 0.55f;
    } else {
      tempIncreaseChance = 0.5f;
    }

    if (temperature < tempTarget) {
      changeTemperature(tempIncreaseChance);
    } else {
      changeTemperature(1 - tempIncreaseChance);
    }
  }

  /**
   * Changes the temperature up or down based on the given chance to increase.
   * If the temperature is lower than the target temperature, the chance to increase is higher,
   * while if it's higher than the target temperature, the chance to decrease is higher.
   *
   * @param increaseChance The chance to increase the temperature.
   */
  private void changeTemperature(float increaseChance) {
    float temperatureChange = (float) (Math.random() * 2); // 0-2°C
    if (Math.random() < increaseChance) {
      temperature += temperatureChange;
    } else temperature -= temperatureChange;
  }


public float getHumidity() {
  return humidity;
}

public double getLight() {
  return light;
}

public double getTemperature() {
  return temperature;
}
}
