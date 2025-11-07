package greenhouse.entities.sensors;

public class MotionSensor<T> extends Sensor<T> {
  public MotionSensor(int id, String location, T minimumReading, T maximumReading) {
    super("MotionSensor", id, location, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
