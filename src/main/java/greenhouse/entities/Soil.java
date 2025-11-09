package greenhouse.entities;

/**
 * <p>
 *   Represents a soil sample with a ph value and
 *   moisture level.
 * </p>
 * <ul>
 *   <li>
 *
 *   </li>
 * </ul>
 */
public class Soil extends ClockSubscriber implements Sensorable {
  private double soilHumidity; //The percentage of humidity of the soul
  private float phValue; //The ph value of the soil.

  /**
   * Creates an instance of the Soil.
   *
   * @param soilHumidity The initial value of the soil humidity.
   * @param phValue The initial value of the ph of the soil.
   */
  public Soil(double soilHumidity, float phValue) {
    this.phValue = phValue;
    this.soilHumidity = soilHumidity;
    subscribe();
  }

  /**
   * Interface method, to make all Sensorable objects update their state values.
   */
  @Override
  public void updateState() {

  }

  @Override
  void tick() {

  }
}
