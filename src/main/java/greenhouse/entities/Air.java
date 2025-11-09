package greenhouse.entities;

import java.util.Random;

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
    // light (lux): 0.0 - 1000.0
    if (light == 0.0 && !initialLightSet) {
      light = (double) (Math.random() * 100000);
      initialLightSet = true;
    }
    // temperature (°C): 0.0 - 40.0
    if (temperature == 0.0 && !initialTempSet) {
      temperature = (double) (Math.random() * 40);
      initialTempSet = true;
    }

    if (temperature < tempTarget || temperature > tempTarget) {
      handleTemperature();
    } else if (temperature == tempTarget) {
      changeTemperature(0.5f);
    }
  }

  private void handleTemperature() {
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

  private void changeTemperature(float increaseChance){
    //TODO: implement
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
