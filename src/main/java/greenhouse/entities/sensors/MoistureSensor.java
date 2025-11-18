package greenhouse.entities.sensors;

import greenhouse.entities.Soil;
import greenhouse.entities.SoilSubscriber;

/**
 * Represents a sensor that measures the moisture in the soil in
 * a greenhouse.
 *
 * @param <T>
 */
public class MoistureSensor<T> extends Sensor<T> implements SoilSubscriber {
  private double latestMoistureReading;

  /**
   * Creates an instance of the MoistureSensor.
   *
   * @param id The id of the moisture sensor.
   * @param soil The soil whose moisture the sensor measures.
   */
  public MoistureSensor(int id, Soil soil) {
    super("MoistureSensor", id);
    update(soil);
  }

  /**
   * Subscribes the moisture sensor to the given soil.
   *
   * @param soil The soil whose moisture the sensor measures.
   */
  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  @Override
  public void update(Soil soil) {
    this.latestMoistureReading = soil.getSoilMoisture();
  }

  @Override
  public String toString() {
    return "MoistureSensor{" +
            "id=" + getId() +
            ", latestMoistureReading=" + latestMoistureReading +
            '}';
  }

  public double getMoisture() {
    return latestMoistureReading;
  }
}
