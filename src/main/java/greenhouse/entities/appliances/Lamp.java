package greenhouse.entities.appliances;

/**
 * Appliance responsible for raising light level in a greenhouse.
 */
public class Lamp extends Appliance {

  /**
   * Constructs a new Lamp with the specified parameters.
   * 
   * @param type the type identifier for this appliance
   * @param id the unique identifier for this lamp.
   */
  public Lamp(int id) {
    super("Lamp", id);
  }
  
}
