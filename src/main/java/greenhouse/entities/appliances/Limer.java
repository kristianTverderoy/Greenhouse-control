package greenhouse.entities.appliances;

import greenhouse.logic.Soil;

/**
 * Represents a machine that adds lime to the soil to raise the ph
 * of the soil on command.
 */
public class Limer extends SoilAppliance {

  /**
   * Creates an instance of the Limer.
   *
   * @param id The id of the limer.
   */
  public Limer(int id) {
    super("Limer", id);
  }

  /**
   * Activates the limer by making it raise the ph of the
   * soil.
   */
  @Override
  public void actuate() {
    super.getSoil().lime();
  }
}
