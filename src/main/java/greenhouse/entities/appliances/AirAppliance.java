package greenhouse.entities.appliances;

public abstract class AirAppliance extends Appliance{
  protected boolean isOn = false;

  /**
   * Constructs a new Appliance with the specified parameters.
   *
   * @param type the type of appliance (e.g., "humidifier", "lamp", "sprinkler")
   * @param id   the unique identifier for this appliance
   */
  public AirAppliance(String type, int id) {
    super(type, id);
  }

  /**
   * Changes isOn to false if it was true and vice versa.
   */
  public void togglePower() {
    this.isOn = !this.isOn;
  }

  /**
   * Returns the true or false if the appliance is on.
   *
   * @return the value of isOn
   */
  public boolean getPowerState() {
    return this.isOn;
  }

  @Override
  public String toString() {
    String status = isOn ? "on" : "off";
    return String.format("Appliance [Type: %s, ID: %d, Status: %s]", super.getType(), super.getId(), status);
  }

  /**
   * Turns the appliance on or off.
   */
  @Override
  public void actuate() {
    togglePower();
  }
}
