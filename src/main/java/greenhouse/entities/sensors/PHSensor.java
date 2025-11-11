package greenhouse.entities.sensors;

public class PHSensor<T> extends Sensor<T>{

    private double latestPhReading;
//    private PHActuator actuator;

    public PHSensor(int id, T minimumReading, T maximumReading) {
        super("PHSensor", id, minimumReading, maximumReading);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
