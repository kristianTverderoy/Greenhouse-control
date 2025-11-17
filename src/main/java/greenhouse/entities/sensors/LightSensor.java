package greenhouse.entities.sensors;

import greenhouse.entities.Air;
import greenhouse.entities.AirSubscriber;

/**
 * Represents
 *
 * @param <T>
 */
public class LightSensor<T> extends Sensor<T> implements AirSubscriber {

  private double latestLuxReading;

  public LightSensor(int id, Air air) {
    super("LightSensor", id);
    update(air);
  }

  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

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

  public double getLux() {
    return latestLuxReading;
  }
}
