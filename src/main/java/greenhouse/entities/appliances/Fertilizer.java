package greenhouse.entities.appliances;

public class Fertilizer extends SoilAppliance {


  public Fertilizer(int id) {
    super("Fertilizer", id);
  }

  @Override
  public void actuate() {
    super.getSoil().useFertilizer();
  }
}
