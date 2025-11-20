package greenhouse.entities.filehandling;

/**
 * Data Transfer Object for appliance data.
 * Contains appliance ID, type, and power state for serialization.
 * Made using AI.
 */
public class ApplianceDTO {
  private int id;
  private String type;
  private boolean isOn;

  /**
   * Default constructor for Gson deserialization.
   */
  public ApplianceDTO() {
  }

  /**
   * Creates an ApplianceDTO with ID, type, and power state.
   *
   * @param id The appliance's unique identifier
   * @param type The appliance type (e.g., "Humidifier", "Sprinkler")
   * @param isOn Whether the appliance is currently on
   */
  public ApplianceDTO(int id, String type, boolean isOn) {
    this.id = id;
    this.type = type;
    this.isOn = isOn;
  }

  /**
   * Gets the appliance ID.
   *
   * @return The appliance ID
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the appliance ID.
   *
   * @param id The appliance ID to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the appliance type.
   *
   * @return The appliance type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the appliance type.
   *
   * @param type The appliance type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Checks if the appliance is on.
   *
   * @return true if the appliance is on, false otherwise
   */
  public boolean isOn() {
    return isOn;
  }

  /**
   * Sets the power state of the appliance.
   *
   * @param on true to turn on, false to turn off
   */
  public void setOn(boolean on) {
    isOn = on;
  }
}
