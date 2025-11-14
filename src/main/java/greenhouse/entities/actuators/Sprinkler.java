package greenhouse.entities.actuators;

/**
 * Actuator responsible for watering plants in the greenhouse.
 * The sprinkler controls water distribution to maintain optimal moisture levels
 * for plant growth.
 */
public class Sprinkler extends Actuator {

  /**
   * Constructs a new Sprinkler with the specified parameters.
   *
   * @param type the type identifier for this actuator
   * @param id the unique identifier for this sprinkler
   */
  public Sprinkler(int id) {
    super("Sprinkler", id);
  }

}