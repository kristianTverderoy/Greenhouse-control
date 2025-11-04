package greenhouse.entities;

public class Air implements Sensorable{

  private float humidity; // 0 <= x <= 1
  private double light;
  private double temperature;


  public Air(){

  }

  /**
   * Interface method, to make all Sensorable objects update their state values.
   */
  @Override
  public void updateState() {

  }
}
