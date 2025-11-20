package greenhouse.entities.sensors;

import greenhouse.entities.SoilSubscriber;
import greenhouse.logic.Soil;

/**
 * Represents a sensor measuring nitrogen in soil.
 *
 * @param <T>
 */
public class NitrogenSensor<T> extends Sensor<T> implements SoilSubscriber {
  private double latestNitrogenReading;

  /**
   * Creates an instance of the NitrogenSensor.
   *
   * @param id The id of the nitrogen sensor.
   * @param soil The soil which the sensor measures the nitrogen of.
   */
  public NitrogenSensor(int id, Soil soil) {
    super("NitrogenSensor", id);
    update(soil);
  }

  /**
   * Subscribes the sensor to the given soil.
   *
   * @param soil The soil whose nitrogen the sensor measures.
   */
  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  /**
   * Alerts the nitrogen sensor that the nitrogen level in the given soil has changed,
   * and updates the latest nitrogen reading accordingly.
   *
   * @param soil The soil whose nitrogen level has just been changed..
   */
  @Override
  public void update(Soil soil) {
    latestNitrogenReading = soil.getNitrogen();
  }

  /**
   * Returns the latest nitrogen reading of the soil.
   *
   * @return The latest nitrogen level of the soil.
   */
  public double getNitrogen() {
    return latestNitrogenReading;
  }

  @Override
  public String toString() {
    return "NitrogenSensor{" +
        "id=" + getId() +
        ", latestNitrogenReading=" + latestNitrogenReading +
        '}';
  }

}
