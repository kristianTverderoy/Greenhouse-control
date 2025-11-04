package greenhouse.entities;

public class Soil implements Sensorable{


  private float soilHumidity;
  private double phValue;

  /**
   * Interface method, to make all Sensorable objects update their state values.
   */
  @Override
  public void updateState() {

  }
}
