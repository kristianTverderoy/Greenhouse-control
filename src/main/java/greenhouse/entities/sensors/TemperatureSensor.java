package greenhouse.entities.sensors;

public class TemperatureSensor<T> extends Sensor<T> {


  public TemperatureSensor(int id, String location, T minimumReading, T maximumReading) {
    super("TemperatureSensor", id, location, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
