package greenhouse.entities.appliances;

/**
 * Appliance responsible for temperature control in the greenhouse.
 * Can operate in cooling or heating mode to maintain optimal temperature.
 */
public class Aircondition extends AirAppliance {

  private boolean cooling = false;
  private boolean heating = false;

  /**
   * Appliance responsible for lowering or raising temperature.
  */
  public Aircondition(int id) {
    super("Aircondition", id);
  }

  /**
   * Starts the cooling mode. If heating is active, it will be turned off.
   * Automatically turns on the unit if it's off.
   */
  public void startCooling() {
    if (!getPowerState()) {
      togglePower();
    }
    if (this.heating) {
      this.heating = false;
    }
    this.cooling = true;
  }

  /**
   * Starts the heating mode. If cooling is active, it will be turned off.
   * Automatically turns on the unit if it's off.
   */
  public void startHeating() {
    if (!getPowerState()) {
      togglePower();
    }
    if (this.cooling) {
      this.cooling = false;
    }
    this.heating = true;
  }

  /**
   * Checks if the air condition is currently in heating mode.
   *
   * @return true if heating, false otherwise
   */
  public boolean isHeating() {
    return this.heating;
  }

  /**
   * Checks if the air condition is currently in cooling mode.
   *
   * @return true if cooling, false otherwise
   */
  public boolean isCooling() {
    return this.cooling;
  }

  /**
   * Toggles the power state of the air condition.
   * When turned off, both cooling and heating modes are disabled.
   */
  @Override
  public void togglePower() {
    this.isOn = !this.isOn;
    if (!this.isOn) {
      this.cooling = false;
      this.heating = false;
    }
  }

  @Override
  public void actuate() {
    togglePower();
  }
  
}
