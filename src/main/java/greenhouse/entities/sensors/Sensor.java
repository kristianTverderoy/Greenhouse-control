package greenhouse.entities.sensors;


/**
 * Abstract base class for all sensor types in the greenhouse system.
 * This class provides common functionality for managing sensor state, readings,
 * and alert thresholds.
 *
 * @param <T> the type of data this sensor reads (e.g., Double, Integer)
 */
public abstract class Sensor<T> {
  private int id;
  private final String type;

  /**
   * Constructs a new Sensor with the specified parameters.
   *
   * @param type the type of sensor (e.g., "temperature", "humidity", "soil moisture")
   * @param id the unique identifier for this sensor
   */
  public Sensor(String type, int id) {
    this.id = id;
    this.type = type;
  }

  /**
   * Sets the sensor's id to an int value.
   *
   * @param newId The new unique identifier to assign to this sensor.
   */
  public void setId(int newId) {
    this.id = newId;
  }

  /**
   * Gets the unique identifier of this sensor.
   *
   * @return the sensor's ID
   */
  public int getId() {
    return this.id;
  }


  /**
   * Gets the type of this sensor.
   *
   * @return the sensor type
   */
  public String getType() {
    return this.type;
  }


  @Override
  public abstract String toString();
}
