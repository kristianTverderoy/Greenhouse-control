package greenhouse.entities.sensors;

import greenhouse.entities.AirSubscriber;
import greenhouse.logic.Air;

/**
 * Represents a sensor reading the humidity of the air inside a greenhouse.
 *
 * @param <T> The type of data the sensor reads.
 */
public class HumiditySensor<T> extends Sensor<T> implements AirSubscriber {
  private Float latestHumidityReading;

  /**
   * Creates an instance of the HumiditySensor.
   *
   * @param id The id of the HumiditySensor.
   * @param air The Air that the HumiditySensor reads the
   *            humidity from.
   */
  public HumiditySensor(int id, Air air) {
    super("HumiditySensor", id);
    update(air);
  }

  /**
   * Subscribes the HumiditySensor to the given Air.
   *
   * @param air The air the HumiditySensor subscribes to.
   */
  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

  /**
   * Alerts the HumiditySensor that the humidity of the air has just been changed, and
   * updates the latest humidity reading to the new humidity value.
   *
   * @param air The air whose humidity has just changed.
   */
  @Override
  public void update(Air air) {
    this.latestHumidityReading = air.getHumidity();
  }
  
  @Override
  public String toString() {
    return "HumiditySensor{" +
            "id=" + getId() +
            ", humidityLevel=" + this.latestHumidityReading +
            '}';
  }

  /**
   * Returns the current humidity reading.
   *
   * @return The current humidity reading.
   */
  public double getHumidity() {
    return latestHumidityReading;
  }


}
