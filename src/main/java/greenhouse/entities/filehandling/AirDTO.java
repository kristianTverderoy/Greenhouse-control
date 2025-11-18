package greenhouse.entities.filehandling;

public class AirDTO {
  private float humidity;
  private double lux;
  private double temperature;
  private Float targetHumidity;
  private Double targetTemperature;
  private Double targetLux;

  public AirDTO() {
  }

  public AirDTO(float humidity, double lux, double temperature, 
                Float targetHumidity, Double targetTemperature, Double targetLux) {
    this.humidity = humidity;
    this.lux = lux;
    this.temperature = temperature;
    this.targetHumidity = targetHumidity;
    this.targetTemperature = targetTemperature;
    this.targetLux = targetLux;
  }

  public float getHumidity() {
    return humidity;
  }

  public void setHumidity(float humidity) {
    this.humidity = humidity;
  }

  public double getLux() {
    return lux;
  }

  public void setLux(double lux) {
    this.lux = lux;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public Float getTargetHumidity() {
    return targetHumidity;
  }

  public void setTargetHumidity(Float targetHumidity) {
    this.targetHumidity = targetHumidity;
  }

  public Double getTargetTemperature() {
    return targetTemperature;
  }

  public void setTargetTemperature(Double targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  public Double getTargetLux() {
    return targetLux;
  }

  public void setTargetLux(Double targetLux) {
    this.targetLux = targetLux;
  }
}
