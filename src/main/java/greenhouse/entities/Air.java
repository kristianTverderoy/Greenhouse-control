package greenhouse.entities;

import java.util.ArrayList;

public class Air extends ClockSubscriber implements Sensorable {

  private float humidity; // 0 <= x <= 1
  private double lux; //The amount of light in lux. Lux is a unit of illuminance per square meter.
  private double actualTemperature;
  private Double temperatureTarget;
  private final double defaultTemperature = 15;
  private Float humidityTarget;
  private final float defaultHumidity = 0.65f;
  private Double luxTarget;
  private boolean initialTempSet = false;
  private boolean initialLightSet = false;
  private boolean initialHumiditySet = false;
  private int currentHour = 0;
  private ArrayList<AirSubscriber> subscribers;
  
  /**
   * Creates an instance of the Air, with target temperature,
   * humidity and light level.
   *
   * @param tempTarget     The target temperature of the air.
   * @param humidityTarget The target humidity of the air.
   * @param luxTarget      The target light level of the air.
   */
  public Air(double tempTarget, float humidityTarget, double luxTarget) {
    this.temperatureTarget = tempTarget;
    this.humidityTarget = humidityTarget;
    this.luxTarget = luxTarget;
    subscribers = new ArrayList<>();
    subscribe();
  }

  @Override
  void tick() {
    updateState();
  }


  /**
   * Interface method, to make all Sensorable objects update their state values.
   */
  @Override
  public void updateState() {
    currentHour = (currentHour + 1) % 24;
    // humidity: 0.0 - 1.0
    if (humidity == 0.0f && !initialHumiditySet) {
      humidity = (float) (Math.random());
      initialHumiditySet = true;
    }
    // light (lux): 0.0 - 100000.0
    if (lux == 0.0 && !initialLightSet) {
      lux = Math.random() * 100000;
      initialLightSet = true;
    }
    // temperature (°C): 0.0 - 40.0
    if (actualTemperature == 0.0 && !initialTempSet) {
      actualTemperature = Math.random() * 40;
      initialTempSet = true;
    }

    updateLight();
    humidityChangeProbability();
    tempChangeProbability();
  }

  /**
   * Updates the light level based on a 24-hour sine wave pattern,
   * and multiplying with a random value from 0.8 to 1.2,
   * simulating natural daylight variations.
   */
  private void updateLight() {
    // Calculate base lux from 24-hour sine wave (range: 20 to 70,020 lux)
    double randomMultiplier = 0.8 + Math.random() * 0.4;
    double sineValue = Math.sin((currentHour / 24.0) * 2 * Math.PI - Math.PI / 2);

    // Map sine wave from [-1, 1] to [30, 40,000] lux
    // Multiplied by random multiplier to simulate weather variations
    this.lux = randomMultiplier * (20015 + 19985 * sineValue);
  }

  /**
   * Simulates humidity change probability based on the difference
   * between current humidity and target humidity.
   */

  private void humidityChangeProbability() {
    float humidityIncreaseChance;
    if (Math.abs(humidity - humidityTarget) > 0.5) {
      humidityIncreaseChance = 0.97f;
    } else if (Math.abs(humidity - humidityTarget) > 0.2) {
      humidityIncreaseChance = 0.92f;
    } else if (Math.abs(humidity - humidityTarget) > 0.01) {
      humidityIncreaseChance = 0.88f;
    } else {
      humidityIncreaseChance = 0.5f;
    }
    if (humidity < humidityTarget) {
      changeHumidity(humidityIncreaseChance);
    } else {
      changeHumidity(1 - humidityIncreaseChance);
    }
  }

  private void changeHumidity(float increaseChance) {
    float humidityChange = (float) ((Math.random() * 0.03) + 0.005); // 0.5-3.5%
    if (Math.random() < increaseChance) {
      humidity += humidityChange;
    } else {
      humidity -= humidityChange;
    }
  }

  /**
   * Simulates temperature change probability based on the difference
   * between current temperature and target temperature.
   */
  private void tempChangeProbability() {
    float tempIncreaseChance;
    if (Math.abs(actualTemperature - temperatureTarget) > 20) {
      tempIncreaseChance = 0.97f;
    } else if (Math.abs(actualTemperature - temperatureTarget) > 10) {
      tempIncreaseChance = 0.92f;
    } else if (Math.abs(actualTemperature - temperatureTarget) > 2) {
      tempIncreaseChance = 0.85f;
    } else {
      tempIncreaseChance = 0.5f;
    }

    if (actualTemperature < temperatureTarget) {
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
    float temperatureChange = (float) (Math.random() + 0.25); // 0.25-1.25°C
    if (Math.random() < increaseChance) {
      actualTemperature += temperatureChange;
    } else {
      actualTemperature -= temperatureChange;
    }
    
  }

  /**
   * Sets the targetTemperature to a new desired temperature.
   */
  public void setTargetTemperature(double newTemperatureTarget) {
    this.temperatureTarget = newTemperatureTarget;
  }

  public float getHumidity() {
    return humidity;
  }

  public double getLux() {
    return lux;
  }

  public double getTemperature() {
    return actualTemperature;
  }

  public Float getTargetHumidity() {
    return humidityTarget;
  }

  public Double getTargetTemperature() {
    return temperatureTarget;
  }

  public Double getLuxTarget() {
    return luxTarget;
  }

  /**
   * Adds a subscriber to the list of subscribers of this air.
   * 
   * @param subscriber The subscriber being added to the list of
   *                   subscribers.
   */
  public void addSubscriber(AirSubscriber subscriber) {
    this.subscribers.add(subscriber);
  }

  /**
   * Alerts the airSubscribers that the air has changed.
   */
  public void update() {
    subscribers.forEach(subscriber -> subscriber.update(this));
  }

}
