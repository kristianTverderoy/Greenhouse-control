package greenhouse.entities.appliances;

public class Limer extends SoilAppliance {

  //TODO: Finish class (Frida)
  // Changes PH value
  public Limer(int id) {
    super("Limer", id);
  }

  @Override
  public void actuate() {
    super.getSoil().lime();
  }
}
