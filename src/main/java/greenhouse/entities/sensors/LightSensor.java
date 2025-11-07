package greenhouse.entities.sensors;

public class LightSensor<T> extends Sensor<T> {
  public LightSensor(int id, String location, T minimumReading, T maximumReading) {
    super("LightSensor", id, location, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }


}
