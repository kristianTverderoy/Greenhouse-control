package greenhouse.entities.sensors;

public class MotionSensor<T> extends Sensor<T> {
  public MotionSensor(String id, String location, T minimumReading, T maximumReading) {
    super("MotionSensor", id, location, minimumReading, maximumReading);
  }
}
