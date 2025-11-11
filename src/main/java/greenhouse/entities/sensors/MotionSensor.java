package greenhouse.entities.sensors;

public class MotionSensor<T> extends Sensor<T> {
  public MotionSensor(int id, T minimumReading, T maximumReading) {
    super("MotionSensor", id, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
