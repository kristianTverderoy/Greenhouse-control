package greenhouse.entities.sensors;

public class LightSensor<T> extends Sensor<T> {
  public LightSensor(int id, T minimumReading, T maximumReading) {
    super("LightSensor", id, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }


}
