package greenhouse.entities.sensors;

import greenhouse.entities.SoilSubscriber;
import greenhouse.logic.Soil;

/**
 * Represents a sensor reading the ph of soil.
 *
 * @param <T>
 */
public class PHSensor<T> extends Sensor<T> implements SoilSubscriber {
    private double latestPhReading;

  /**
   * Creates an instance of PHSensor.
   *
   * @param id The id of the ph sensor.
   * @param soil The soil the sensor measures the ph of.
   */
  public PHSensor(int id, Soil soil) {
    super("PHSensor", id);
    update(soil);
  }

  /**
   * Subscribes the ph sensor to the given soil.
   *
   * @param soil The soil the sensor measures the ph of.
   */
  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  /**
   * Alerts the ph sensor that the ph of the given soil has changed,
   * changes the latest reading to the new ph value.
   *
   * @param soil The soil whose ph has just been changed.
   */
  @Override
  public void update(Soil soil) {
    this.latestPhReading = soil.getPhValue();
  }

  @Override
  public String toString() {
    return "PHSensor{" +
            "id=" + getId() +
            ", latestPhReading=" + latestPhReading +
            '}';
  }

  /**
   * Returns the latest ph reading.
   *
   * @return The latest ph reading.
   */
  public double getPh() {
    return latestPhReading;
  }
}
