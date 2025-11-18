package greenhouse.entities.appliances;

/**
 * Appliance responsible for raising light level in a greenhouse.
 */
public class Lamp extends AirAppliance {

  /**
   * Constructs a new Lamp with the specified parameters.
   *
   * @param id the unique identifier for this lamp.
   */
  public Lamp(int id) {
    super("Lamp", id);
  }
  
}
