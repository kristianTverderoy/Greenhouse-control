package greenhouse.entities.filehandling;

/**
 * Data Transfer Object for air state data.
 * Contains current and target values for humidity, temperature, and light levels.
 * Made using AI.
 */
public class AirDTO {
  private float humidity;
  private double lux;
  private double temperature;
  private Float targetHumidity;
  private Double targetTemperature;
  private Double targetLux;

  /**
   * Default constructor for Gson deserialization.
   */
  public AirDTO() {
  }

  /**
   * Creates an AirDTO with all air state data.
   *
   * @param humidity Current humidity level (%)
   * @param lux Current light level (lux)
   * @param temperature Current temperature (°C)
   * @param targetHumidity Target humidity level (%)
   * @param targetTemperature Target temperature (°C)
   * @param targetLux Target light level (lux)
   */
  public AirDTO(float humidity, double lux, double temperature, 
                Float targetHumidity, Double targetTemperature, Double targetLux) {
    this.humidity = humidity;
    this.lux = lux;
    this.temperature = temperature;
    this.targetHumidity = targetHumidity;
    this.targetTemperature = targetTemperature;
    this.targetLux = targetLux;
  }

  /**
   * Gets the current humidity level.
   *
   * @return Current humidity (%)
   */
  public float getHumidity() {
    return humidity;
  }

  /**
   * Sets the current humidity level.
   *
   * @param humidity Current humidity (%) to set
   */
  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  /**
   * Gets the current light level.
   *
   * @return Current light level (lux)
   */
  public double getLux() {
    return lux;
  }

  /**
   * Sets the current light level.
   *
   * @param lux Current light level (lux) to set
   */
  public void setLux(double lux) {
    this.lux = lux;
  }

  /**
   * Gets the current temperature.
   *
   * @return Current temperature (°C)
   */
  public double getTemperature() {
    return temperature;
  }

  /**
   * Sets the current temperature.
   *
   * @param temperature Current temperature (°C) to set
   */
  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  /**
   * Gets the target humidity level.
   *
   * @return Target humidity (%)
   */
  public Float getTargetHumidity() {
    return targetHumidity;
  }

  /**
   * Sets the target humidity level.
   *
   * @param targetHumidity Target humidity (%) to set
   */
  public void setTargetHumidity(Float targetHumidity) {
    this.targetHumidity = targetHumidity;
  }

  /**
   * Gets the target temperature.
   *
   * @return Target temperature (°C)
   */
  public Double getTargetTemperature() {
    return targetTemperature;
  }

  /**
   * Sets the target temperature.
   *
   * @param targetTemperature Target temperature (°C) to set
   */
  public void setTargetTemperature(Double targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  /**
   * Gets the target light level.
   *
   * @return Target light level (lux)
   */
  public Double getTargetLux() {
    return targetLux;
  }

  /**
   * Sets the target light level.
   *
   * @param targetLux Target light level (lux) to set
   */
  public void setTargetLux(Double targetLux) {
    this.targetLux = targetLux;
  }
}
