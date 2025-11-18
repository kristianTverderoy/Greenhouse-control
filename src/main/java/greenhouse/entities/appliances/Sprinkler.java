package greenhouse.entities.appliances;

/**
 * Appliance responsible for watering plants in the greenhouse.
 * The sprinkler controls water distribution to maintain optimal moisture levels
 * for plant growth.
 */
public class Sprinkler extends SoilAppliance {

  /**
   * Constructs a new Sprinkler with the specified parameters.
   *
   * @param id the unique identifier for this sprinkler
   */
  public Sprinkler(int id) {
    super("Sprinkler", id);
  }

  @Override
  public void actuate() {
    super.getSoil().waterSoil(20);
  }
}