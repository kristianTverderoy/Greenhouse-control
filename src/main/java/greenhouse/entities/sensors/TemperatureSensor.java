package greenhouse.entities.sensors;

public class TemperatureSensor<T> extends Sensor<T> {

  public TemperatureSensor(T type, int id, String location, T minimumReading, T maximumReading) {
    super("TemperatureSensor", id, location, minimumReading, maximumReading);
  }
}
