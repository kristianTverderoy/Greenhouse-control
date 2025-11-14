package greenhouse.entities.actuators;

/**
 * Abstract class for all actuator types in the greenhouse system.
 * This class provides common functionality for managing power state and id.
 */
public abstract class Actuator {
  private String type;
  private int id;
  protected boolean isOn = false;

  /**
   * Constructs a new Actuator with the specified parameters.
   * 
   * @param type the type of actuator (e.g., "humidifier", "lamp", "sprinkler")
   * @param id the unique identifier for this actuator
   * 
   */
  public Actuator(String type, int id) {
    this.type = type;
    setId(id);
  }
  
  /**
   * Sets the id of the actuator
   * @param id the id of the actuator
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
   * Returns the true or false if the actuator is on.
   * @return the value of isOn
   */
  public boolean getPowerState() {
    return this.isOn;
  }
}
