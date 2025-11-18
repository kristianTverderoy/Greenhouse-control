package greenhouse.entities.appliances;

/**
 * Appliance responsible for raising humidity in the air.
 * The humidifier can adjust the rate at which it increases humidity levels
 * in the greenhouse environment.
 * 
 * <p>This appliance extends {@link Appliance} with Float type parameter,
 * representing humidity percentage values.
 */
public class Humidifier extends Appliance {


  /**
   * Constructs a new Humidifier with the specified parameters.
   *
   * @param id the unique identifier for this humidifier
   */
  public Humidifier(int id) {
    super("Humidifier", id);
  }

  
}
