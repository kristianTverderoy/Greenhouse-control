package greenhouse.entities.appliances;

/**
 * Represents a machine that fertilizes the soil on command.
 */
public class Fertilizer extends SoilAppliance {

  /**
   * Creates an instance of the Fertilizer.
   *
   * @param id The id of the fertilizer.
   */
  public Fertilizer(int id) {
    super("Fertilizer", id);
  }

  /**
   * Activates the fertilizer to fertilize the soil.
   */
  @Override
  public void actuate() {
    super.getSoil().fertilize(10);
  }
}
