package greenhouse.entities.appliances;

import greenhouse.entities.Soil;

/**
 * Represents a machine that does actions to the soil
 * on command.
 */
public abstract class SoilAppliance extends Appliance{
  private Soil soil;

  /**
   * Constructs a new SoilAppliance with the specified parameters.
   *
   * @param type the type of appliance (e.g., "humidifier", "lamp", "sprinkler")
   * @param id   the unique identifier for this appliance
   */
  public SoilAppliance(String type, int id) {
    super(type, id);
  }

  /**
   * Adds the soil that the soil appliances does actions to.
   *
   * @param soil The soil that the soil appliances does actions on.
   */
  public void addSoil(Soil soil) {
    this.soil = soil;
  }

  /**
   * Returns the soil that the appliance does actions on.
   *
   * @return The soil that the appliance does actions on.
   */
  public Soil getSoil() {
    return this.soil;
  }
}
