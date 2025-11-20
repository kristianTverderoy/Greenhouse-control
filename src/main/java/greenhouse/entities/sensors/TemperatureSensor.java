package greenhouse.entities.sensors;

import greenhouse.entities.AirSubscriber;
import greenhouse.logic.Air;

/**
 * Represents a sensor which measures the temperature of air.
 *
 * @param <T>
 */
public class TemperatureSensor<T> extends Sensor<T> implements AirSubscriber {
  private double latestTemperatureReading;

  /**
   * Creates an instance of the TemperatureSensor.
   *
   * @param id The id of the temperature sensor.
   * @param air The air which the sensor measures the temperature of.
   */
  public TemperatureSensor(int id,Air air) {
    super("TemperatureSensor", id);
    update(air);
  }

  /**
   * Subscribes the temperature sensor to the given air.
   *
   * @param air The air the sensor measures the temperature of.
   */
  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

  /**
   * Alerts the sensor that the tempersture of the given air has been changed,
   * and updates the latest temperature reading to match the new temperature.
   *
   * @param air The air whose temperature has just been changed.
   */
  @Override
  public void update(Air air) {
    this.latestTemperatureReading = air.getTemperature();
  }

  @Override
  public String toString() {
    return "TemperatureSensor{" +
            "id=" + getId() +
            ", temperature=" + latestTemperatureReading +
            '}';
  }

}
