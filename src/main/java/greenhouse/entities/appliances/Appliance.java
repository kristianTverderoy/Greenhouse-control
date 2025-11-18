package greenhouse.entities.appliances;

/**
 * Abstract class for all appliance types in the greenhouse system.
 * This class provides common functionality for managing power state and id.
 */
public abstract class Appliance {
  private String type;
  private int id;

  /**
   * Constructs a new Appliance with the specified parameters.
   *
   * @param type the type of appliance (e.g., "humidifier", "lamp", "sprinkler")
   * @param id the unique identifier for this appliance
   *
   */
  public Appliance(String type, int id) {
    this.type = type;
    setId(id);
  }
  
  /**
   * Sets the id of the appliance
   * @param id the id of the appliance
   */
  private void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the type of the actuator.
   *
   * @return The type of the actuator.
   */
  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return String.format("Appliance [Type: %s, ID: %d]", type, id);
  }

  /**
   * Returns the id of the appliance.
   *
   * @return The id of the appliance.
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Activates the appliance.
   */
  public abstract void actuate();
}
