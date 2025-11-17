package greenhouse.entities.appliances;

/**
 * Abstract class for all appliance types in the greenhouse system.
 * This class provides common functionality for managing power state and id.
 */
public abstract class Appliance {
  private String type;
  private int id;
  protected boolean isOn = false;

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
   * Changes isOn to false if it was true and vice versa.
   */
  public void togglePower() {
    this.isOn = !this.isOn;
  }

  /**
   * Returns the true or false if the appliance is on.
   * @return the value of isOn
   */
  public boolean getPowerState() {
    return this.isOn;
  }

  @Override
  public String toString() {
    String status = isOn ? "on" : "off";
    return String.format("Appliance [Type: %s, ID: %d, Status: %s]", type, id, status);
  }

  public Integer getId() {
    return this.id;
  }
}
