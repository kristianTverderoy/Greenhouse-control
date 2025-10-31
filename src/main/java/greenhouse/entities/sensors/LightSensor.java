package greenhouse.entities.sensors;

public class LightSensor<T> extends Sensor<T> {
  public LightSensor(T type, String id, String location, T minimumReading, T maximumReading) {
    super("LightSensor", id, location, minimumReading, maximumReading);
  }
}
