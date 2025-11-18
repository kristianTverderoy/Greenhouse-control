package greenhouse.entities.appliances;

import greenhouse.entities.Soil;

public abstract class SoilAppliance extends Appliance{
  private Soil soil;

  /**
   * Constructs a new Appliance with the specified parameters.
   *
   * @param type the type of appliance (e.g., "humidifier", "lamp", "sprinkler")
   * @param id   the unique identifier for this appliance
   */
  public SoilAppliance(String type, int id) {
    super(type, id);
  }

  public void addSoil(Soil soil) {
    this.soil = soil;
  }

  public Soil getSoil() {
    return this.soil;
  }
}
