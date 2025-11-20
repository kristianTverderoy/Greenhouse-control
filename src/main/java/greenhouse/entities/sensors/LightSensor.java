package greenhouse.entities.sensors;

import greenhouse.entities.AirSubscriber;
import greenhouse.logic.Air;

/**
 * Represents a light sensor in a green house.
 *
 * @param <T>
 */
public class LightSensor<T> extends Sensor<T> implements AirSubscriber {
  private double latestLuxReading;

  /**
   * creates an instance of the LightSensor.
   *
   * @param id The id of the light sensor.
   * @param air The air that the LightSensor measures the light
   *            from.
   */
  public LightSensor(int id, Air air) {
    super("LightSensor", id);
    update(air);
  }

  /**
   * Subscribes the LightSensor to the given air.
   *
   * @param air The air the LightSensor measures the light
   *            in.
   */
  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

  /**
   * Alerts the light sensor that the light level in the given air has changed,
   * and updates the latest light reading accordingly.
   *
   * @param air The air whose light level has just changed.
   */
  @Override
  public void update(Air air) {
    this.latestLuxReading = air.getLux();
  }

  @Override
  public String toString() {
    return "LightSensor{" +
            "id=" + getId() +
            ", lux=" + latestLuxReading +
            '}';
  }

  /**
   *Returns the latest light reading of the sensor.
   *
   * @return The latest light reading in lux.
   */
  public double getLux() {
    return latestLuxReading;
  }
}
