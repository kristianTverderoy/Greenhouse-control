package greenhouse.entities.sensors;

public class PHSensor<T> extends Sensor<T> {

    private double latestPhReading;
//    private PHActuator actuator;

    public PHSensor(int id) {
        super("PHSensor", id);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
