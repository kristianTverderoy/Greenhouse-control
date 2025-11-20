package greenhouse.filehandling;

/**
 * Data Transfer Object for sensor data.
 * Contains sensor ID and type information for serialization.
 * Made using AI.
 */
public class SensorDTO {
  private int id;
  private String type;

  /**
   * Default constructor for Gson deserialization.
   */
  public SensorDTO() {
  }

  /**
   * Creates a SensorDTO with ID and type.
   *
   * @param id The sensor's unique identifier
   * @param type The sensor type (e.g., "HumiditySensor", "TemperatureSensor")
   */
  public SensorDTO(int id, String type) {
    this.id = id;
    this.type = type;
  }

  /**
   * Gets the sensor ID.
   *
   * @return The sensor ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the sensor ID.
   *
   * @param id The sensor ID to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the sensor type.
   *
   * @return The sensor type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the sensor type.
   *
   * @param type The sensor type to set
   */
  public void setType(String type) {
    this.type = type;
  }
}
