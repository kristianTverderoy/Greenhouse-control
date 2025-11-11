package greenhouse.entities.sensors;

public class HumiditySensor<T> extends Sensor<T>{


    public HumiditySensor(int id, T minimumReading, T maximumReading) {
        super("HumiditySensor", id, minimumReading, maximumReading);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
