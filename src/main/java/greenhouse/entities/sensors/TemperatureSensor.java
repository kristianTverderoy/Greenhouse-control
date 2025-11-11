package greenhouse.entities.sensors;

public class TemperatureSensor<T> extends Sensor<T> {


  public TemperatureSensor(int id, T minimumReading, T maximumReading) {
    super("TemperatureSensor", id);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {
    
  }
}
