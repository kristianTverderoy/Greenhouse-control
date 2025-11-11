package greenhouse.entities.actuators;

/**
 * Actuator responsible for raising light level in a greenhouse.
 */
public class Lamp extends Actuator {

  /**
   * Constructs a new Lamp with the specified parameters.
   * 
   * @param type the type identifier for this actuator
   * @param id the unique identifier for this lamp.
   */
  public Lamp(String type, int id) {
    super(type, id);
  }
  
}
