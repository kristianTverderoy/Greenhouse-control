package greenhouse.entities.sensors;

public class HumiditySensor<T> extends Sensor<T>{


    public HumiditySensor(int id, String location, T minimumReading, T maximumReading) {
        super("HumiditySensor", id, location, minimumReading, maximumReading);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
